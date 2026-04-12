package com.ziad.pokeappcompose.domain.repository

import com.ziad.pokeappcompose.domain.model.pokemon.PokemonResponse
import com.ziad.pokeappcompose.domain.model.pokemon.detail.PokemonDetailResponse
import com.ziad.pokeappcompose.utils.ResultState

interface IPokemonRepository {
    suspend fun getListPokemon(
        offset: String? = "0",
        limit: String? = "10"
    ): ResultState<PokemonResponse>


    suspend fun getDetailPokemon(
        url: String? = ""
    ): ResultState<PokemonDetailResponse>
}