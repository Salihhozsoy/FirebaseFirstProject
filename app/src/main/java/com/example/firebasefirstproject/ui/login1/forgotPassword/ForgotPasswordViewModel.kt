package com.example.firebasefirstproject.ui.login1.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.state.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _forgotPasswordState: MutableSharedFlow<ForgotPasswordState> = MutableSharedFlow()
    val forgotPasswordState: MutableSharedFlow<ForgotPasswordState> = _forgotPasswordState
    fun resetPassword(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                _forgotPasswordState.emit(ForgotPasswordState.Loading)
                userRepository.sendPasswordResetEmail(email)
                _forgotPasswordState.emit(ForgotPasswordState.Result)
            }.onFailure {
                _forgotPasswordState.emit(ForgotPasswordState.Error(it))
            }
        }
    }
}