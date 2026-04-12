package com.ziad.pokeappcompose.di

import com.ziad.pokeappcompose.data.local.PokeLocalDataSource
import com.ziad.pokeappcompose.data.local.UserLocalDataSource
import com.ziad.pokeappcompose.data.remote.PokemonAPIService
import com.ziad.pokeappcompose.data.repository.PokemonRepositoryImpl
import com.ziad.pokeappcompose.data.repository.UserRepositoryImpl
import com.ziad.pokeappcompose.domain.repository.IPokemonRepository
import com.ziad.pokeappcompose.domain.repository.IUserRepository
import com.ziad.pokeappcompose.utils.NetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserLocalDataSource
    ): IUserRepository {
        return UserRepositoryImpl(userDataSource)
    }


    @Provides
    @Singleton
    fun providePokemonRepository(
        apiService: PokemonAPIService,
        pokeLocalDataSource: PokeLocalDataSource,
        networkObserver: NetworkObserver
    ): IPokemonRepository {
        return PokemonRepositoryImpl(apiService, pokeLocalDataSource, networkObserver)
    }
}