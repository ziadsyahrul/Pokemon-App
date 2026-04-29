package com.ziad.pokeappcompose.domain.model.pokemon.detail

data class PokemonDetailResponse(
    val id: Int = 0,
    val name: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val abilities: List<AbilitiesData> = emptyList(),
    val forms: List<FormsItem> = emptyList(),
    val sprites: SpritesItem? = null,
    val stats: List<StatsData> = emptyList(),
    val types: List<TypesData> = emptyList()
)