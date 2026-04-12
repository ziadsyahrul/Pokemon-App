package com.ziad.pokeappcompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziad.pokeappcompose.domain.repository.IPokemonRepository
import com.ziad.pokeappcompose.presentation.ui.detail.DetailUIState
import com.ziad.pokeappcompose.presentation.ui.home.HomeUIState
import com.ziad.pokeappcompose.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IPokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUIState>(DetailUIState.Idle)
    val detailUiState = _detailUiState.asStateFlow()


    private var currentOffset = 0
    private val pageSize = 10
    private var isRequestRunning = false

    init {
        getPokemon()
    }

    fun getPokemon() {
        if (isRequestRunning) return

        if (_uiState.value.endReached) return

        viewModelScope.launch {
            isRequestRunning = true

            if (currentOffset == 0) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            } else {
                _uiState.value = _uiState.value.copy(isPaginationLoading = true)
            }

            val result = repository.getListPokemon(
                offset = currentOffset.toString(),
                limit = pageSize.toString()
            )


            when (result) {
                is ResultState.Success -> {
                    val newItems = result.data?.results ?: emptyList()

                    val updatedList = _uiState.value.pokemonList + newItems

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isPaginationLoading = false,
                        pokemonList = updatedList,
                        filteredList = updatedList,
                        endReached = newItems.isEmpty(),
                        isFromCached = result.data?.isFromCached
                    )

                    currentOffset += pageSize
                }

                is ResultState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isPaginationLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }

            isRequestRunning = false
        }
    }

    fun onSearchQuery(query: String) {
        val currentState = _uiState.value
        val baseList = currentState.pokemonList

        if (query == currentState.searchQuery) return

        val filteredList = if (query.isBlank()) {
            baseList
        } else {
            baseList.filter { pokemon ->
                pokemon.name?.contains(query.trim(), ignoreCase = true) ?: false
            }
        }

        _uiState.value = currentState.copy(
            searchQuery = query,
            filteredList = filteredList
        )
    }

    fun getDetailPokemon(url: String) {
        Log.d("DetailDebug", "getDetailPokemon called with url: $url")
        if (url.isBlank()) return

        viewModelScope.launch {
            _detailUiState.value = DetailUIState.Loading

            try {
                when (val result = repository.getDetailPokemon(url)) {
                    is ResultState.Success -> {
                        Log.d("DetailDebug", "Success: ${result.data}")
                        val data = result.data
                        if (data != null) {
                            _detailUiState.value = DetailUIState.Success(data)
                        } else {
                            _detailUiState.value = DetailUIState.Error("Empty Response Detail")
                        }
                    }

                    is ResultState.Error -> {
                        Log.d("DetailDebug", "Error: ${result.message}")
                        _detailUiState.value =
                            DetailUIState.Error(result.message ?: "Unknown Error")
                    }

                    else -> Unit
                }
            } catch (e: Exception) {
                Log.d("DetailDebug", "Exception: ${e.message}")
                _detailUiState.value = DetailUIState.Error(
                    e.message ?: "Unexpected Catch Error"
                )
            }
        }
    }
}