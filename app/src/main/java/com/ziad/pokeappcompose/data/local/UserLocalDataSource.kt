package com.ziad.pokeappcompose.data.local

import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Expression
import com.couchbase.lite.Meta
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.ziad.pokeappcompose.domain.model.user.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val database: Database
) {

    fun register(user: User): Boolean {
        return try {
            val doc = MutableDocument()
                .setString(TYPE, USER)
                .setString(USERNAME, user.userName)
                .setString(PASSWORD, hashPassword(user.password))

            database.save(doc)
            return true
        } catch (e: Exception) {
            false
        }
    }


    fun loginUser(username: String, password: String): User? {
        val query = QueryBuilder
            .select(
                SelectResult.expression(Meta.id),
                SelectResult.property(USERNAME),
                SelectResult.property(PASSWORD)
            )
            .from(DataSource.database(database))
            .where(
                Expression.property(TYPE).equalTo(Expression.string(USER))
                    .and(Expression.property(USERNAME).equalTo(Expression.string(username)))
            )
        val result = query.execute()

        val row = result.allResults().firstOrNull() ?: return null

        val storedPassword = row.getString(PASSWORD) ?: return null

        return if (verifyPassword(password, storedPassword)) {
            User(
                id = row.getString("id")?.hashCode() ?: 0,
                userName = row.getString(USERNAME) ?: "",
                password = ""
            )
        } else null
    }

    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }

    private fun verifyPassword(
        input: String,
        stored: String
    ): Boolean {
        return hashPassword(input) == stored
    }

    companion object {
        private const val TYPE = "type"
        private const val USER = "user"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }
}