package com.ziad.pokeappcompose.presentation.ui.detail

import com.ziad.pokeappcompose.domain.model.pokemon.detail.PokemonDetailResponse

sealed class DetailUIState {
    object Idle : DetailUIState()
    object Loading: DetailUIState()
    data class Success(val data: PokemonDetailResponse?) : DetailUIState()
    data class Error(val message: String) : DetailUIState()
}