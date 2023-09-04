package com.example.firebasefirstproject.data.state

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Empty : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object Result : ForgotPasswordState()
    class Error(val throwable: Throwable? = null) : ForgotPasswordState()
}
