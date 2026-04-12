package com.ziad.pokeappcompose.domain.repository

import android.content.res.Resources
import com.ziad.pokeappcompose.domain.model.user.User
import com.ziad.pokeappcompose.utils.ResultState

interface IUserRepository {

    suspend fun register(user: User): Boolean
    suspend fun login(username: String, pass: String): ResultState<User>
}
