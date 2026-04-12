package com.ziad.pokeappcompose.data.repository

import com.ziad.pokeappcompose.data.local.PokeLocalDataSource
import com.ziad.pokeappcompose.data.remote.PokemonAPIService
import com.ziad.pokeappcompose.domain.model.pokemon.PokemonResponse
import com.ziad.pokeappcompose.domain.model.pokemon.detail.PokemonDetailResponse
import com.ziad.pokeappcompose.domain.repository.IPokemonRepository
import com.ziad.pokeappcompose.utils.NetworkObserver
import com.ziad.pokeappcompose.utils.ResultState
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonAPIService,
    private val pokeLocalDataSource: PokeLocalDataSource,
    private val networkObserver: NetworkObserver
) : IPokemonRepository {

    override suspend fun getListPokemon(
        offset: String?,
        limit: String?
    ): ResultState<PokemonResponse> {
        return try {
            if (networkObserver.isOnline()) {
                val response = apiService.getListPokemon(offset, limit)

                if (response.isSuccessful) {
                    val data = response.body()?.results ?: emptyList()

                    pokeLocalDataSource.insertPokemon(data)
                    ResultState.Success(response.body())
                } else {
                    ResultState.Error(response.message() ?: "error")
                }
            } else {
                val cached = PokemonResponse(
                    isFromCached = true,
                    results = pokeLocalDataSource.getListPokemon()
                )
                ResultState.Success(cached)
            }
        } catch (e: Exception) {
            val cached = PokemonResponse(
                isFromCached = true,
                results = pokeLocalDataSource.getListPokemon()
            )
            ResultState.Success(cached)
        }
    }

    override suspend fun getDetailPokemon(url: String?): ResultState<PokemonDetailResponse> {
        return try {
            if (networkObserver.isOnline()) {
                val response = apiService.getDetailPokemon(url.toString())

                if (response.isSuccessful) {
                    ResultState.Success(response.body())
                } else {
                    ResultState.Error(response.message() ?: "Network Error")
                }
            } else {
                ResultState.Error("Network Error")
            }
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Network Error")
        }
    }
}