package com.ziad.pokeappcompose.di

import com.ziad.pokeappcompose.data.remote.PokemonAPIService
import com.ziad.pokeappcompose.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideAPIService(
        client: OkHttpClient
    ): PokemonAPIService {
        return AppModule
            .setupRetrofit(Constant.BASE_URL, client)
            .create(PokemonAPIService::class.java)
    }
}