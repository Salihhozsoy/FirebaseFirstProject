package com.example.firebasefirstproject.ui.login1.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.repository.AuthRepository
import com.example.firebasefirstproject.data.state.AuthState
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState: MutableSharedFlow<AuthState> = MutableSharedFlow()
    val loginState: SharedFlow<AuthState> = _loginState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun login(email: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    _loginState.emit( AuthState.Loading)
                    _loginState.emit(authRepository.login(email, password))
                }else{
                    _message.emit("do not leave fields empty")
                }
            }.onFailure {
                when (it) {
                    is FirebaseAuthInvalidUserException -> {
                        _loginState.emit(AuthState.UserNotFound)
                    }
                    else -> {
                        _loginState.emit(AuthState.Error(it))
                    }
                }
            }
        }
    }
}