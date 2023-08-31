package com.example.firebasefirstproject.data.state

sealed class AuthState{
    object Idle:AuthState()
    object Loading:AuthState()
    object UserNotFound:AuthState()
    object Success:AuthState()
    class Error(val throwable: Throwable?=null):AuthState()
}
