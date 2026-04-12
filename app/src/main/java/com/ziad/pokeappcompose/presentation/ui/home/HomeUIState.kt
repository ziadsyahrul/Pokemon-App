package com.ziad.pokeappcompose.presentation.ui.home

import com.ziad.pokeappcompose.domain.model.pokemon.PokeItem

data class HomeUIState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val pokemonList: List<PokeItem> = emptyList(),
    val filteredList: List<PokeItem> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val endReached: Boolean = false,
    val isFromCached: Boolean? = false
)