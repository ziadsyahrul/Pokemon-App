package com.ziad.pokeappcompose.domain.model.pokemon.detail

data class PokemonDetailResponse(
    val abilities: List<AbilitiesData>,
    val forms: List<FormsItem>,
    val sprites: SpritesItem? = null
)