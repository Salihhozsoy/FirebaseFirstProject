package com.example.firebasefirstproject.ui.login1.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.repository.AuthRepository
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(private val authRepository: AuthRepository,private val userRepository: UserRepository) : ViewModel() {

    private val _registerState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun register(email: String, password: String, passwordAgain: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                if (email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()) {
                    if (password == passwordAgain) {
                        _registerState.value =RegisterState.Loading
                        authRepository.register(email, password).user?.let {
                            val user = User(it.uid,it.email)
                            userRepository.insert(user)
                        }
                        _registerState.value=RegisterState.Success

                    } else {
                        _message.emit("password are not matched")
                    }
                } else {
                    _message.emit("do not leave fields empty")
                }
            }.onFailure {
                _registerState.value=RegisterState.Error(it)
                _message.emit("Error")
            }
        }
    }
}