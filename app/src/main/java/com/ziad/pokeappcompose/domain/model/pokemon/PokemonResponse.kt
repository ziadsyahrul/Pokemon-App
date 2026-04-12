package com.ziad.pokeappcompose.domain.model.pokemon

data class PokemonResponse(
    val results: List<PokeItem>? = null,
    val next: String? = null,
    val count: Int? = 0,
    val isFromCached: Boolean? = false
)
