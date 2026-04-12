package com.ziad.pokeappcompose.data.local

import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.ziad.pokeappcompose.domain.model.pokemon.PokeItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokeLocalDataSource @Inject constructor(
    private val database: Database

){

    fun insertPokemon(data: List<PokeItem>) {
        data.forEach { pokemon ->
            val docId = "pokemon_${pokemon.name}"

            val doc = database.getDocument(docId)?.toMutable()
                ?: MutableDocument(docId)

            doc.setString("type", "pokemon")
            doc.setString("name", pokemon.name)
            doc.setString("url", pokemon.url)

            database.save(doc)
        }
    }

    fun getListPokemon(): List<PokeItem> {
        val query = QueryBuilder
            .select(
                SelectResult.property("name"),
                SelectResult.property("url")
            )
            .from(DataSource.database(database))
            .where(
                Expression.property("type")
                    .equalTo(Expression.string("pokemon"))
            )

        val result = query.execute()

        return result.allResults().map {
            PokeItem(
                name = it.getString("name"),
                url = it.getString("url")
            )
        }
    }
}