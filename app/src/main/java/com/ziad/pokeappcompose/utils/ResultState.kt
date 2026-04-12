package com.ziad.pokeappcompose.utils

sealed class ResultState<T> {
    class Loading<T> : ResultState<T>()
    data class Success<T>(val data: T?) : ResultState<T>()
    data class Error<T>(
        val message: String,
    ) : ResultState<T>()
}