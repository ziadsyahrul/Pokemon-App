package com.ziad.pokeappcompose.data.repository

import com.ziad.pokeappcompose.data.local.UserLocalDataSource
import com.ziad.pokeappcompose.domain.model.user.User
import com.ziad.pokeappcompose.domain.repository.IUserRepository
import com.ziad.pokeappcompose.utils.ResultState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserLocalDataSource
) : IUserRepository {

    override suspend fun register(user: User): Boolean {
        return userDataSource.register(user)
    }

    override suspend fun login(username: String, pass: String): ResultState<User> {
        return try {

            val user = userDataSource.loginUser(username, pass)

            if (user != null) {
                ResultState.Success(user)
            } else {
                ResultState.Error("Invalid username or password, please register first")
            }

        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Login Failed")
        }
    }

}