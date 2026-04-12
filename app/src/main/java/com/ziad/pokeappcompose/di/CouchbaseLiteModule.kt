package com.ziad.pokeappcompose.di

import android.content.Context
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CouchbaseLiteModule {

    @Singleton
    @Provides
    fun provideCouchbaseDatabase(
        @ApplicationContext context: Context
    ): Database {

        CouchbaseLite.init(context)
        val config = DatabaseConfiguration();

        return Database("poke_db", config)
    }
}