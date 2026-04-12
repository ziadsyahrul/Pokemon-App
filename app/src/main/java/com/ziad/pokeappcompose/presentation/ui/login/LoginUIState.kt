package com.ziad.pokeappcompose.presentation.ui.login

sealed class LoginUIState {
    object Idle : LoginUIState()
    object Loading : LoginUIState()
    data class Error(val message: String) : LoginUIState()
    object Success : LoginUIState()
}