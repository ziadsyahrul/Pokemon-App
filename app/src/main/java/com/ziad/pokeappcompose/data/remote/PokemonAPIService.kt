package com.ziad.pokeappcompose.data.remote

import com.ziad.pokeappcompose.domain.model.pokemon.PokemonResponse
import com.ziad.pokeappcompose.domain.model.pokemon.detail.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonAPIService {

    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("offset") offset: String? = "0",
        @Query("limit") limit: String? = "10"
    ): Response<PokemonResponse>

    @GET
    suspend fun getDetailPokemon(
        @Url url: String
    ): Response<PokemonDetailResponse>
}