package com.ziad.pokeappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziad.pokeappcompose.data.local.SessionPreferenceManager
import com.ziad.pokeappcompose.domain.model.user.User
import com.ziad.pokeappcompose.domain.repository.IUserRepository
import com.ziad.pokeappcompose.presentation.ui.login.LoginUIState
import com.ziad.pokeappcompose.presentation.ui.profile.ProfileUIState
import com.ziad.pokeappcompose.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val iUserRepository: IUserRepository,
    private val preferenceManager: SessionPreferenceManager
) : ViewModel() {
    private val _registerResult = MutableSharedFlow<Boolean?>(replay = 0)
    val registerResult get() = _registerResult.asSharedFlow()

    private val _loginState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _profileUiState =
        MutableStateFlow(ProfileUIState(username = preferenceManager.getUsername()))
    val profileUiState = _profileUiState.asStateFlow()

    private val _isLogin = MutableStateFlow(preferenceManager.isLoggedIn())
    val isLogin = _isLogin.asStateFlow()

    fun registerUser(userEntity: User) {
        viewModelScope.launch {
            val result = iUserRepository.register(userEntity)
            _registerResult.emit(result)
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {

            _loginState.value = LoginUIState.Loading

            val result = iUserRepository.login(username, password)

            val newState = when (result) {

                is ResultState.Success -> {
                    val user = result.data

                    if (user != null) {
                        preferenceManager.saveUserLogin(
                            userId = user.id,
                            username = user.userName
                        )
                        LoginUIState.Success
                    } else {
                        LoginUIState.Error("User data invalid")
                    }
                }

                is ResultState.Error -> {
                    LoginUIState.Error(result.message)
                }

                else -> {
                    LoginUIState.Error("Unknown error")
                }
            }

            _loginState.value = newState
        }
    }

    fun resetState() {
        _loginState.value = LoginUIState.Idle
    }

    fun logout() {
        preferenceManager.logout()
    }
}