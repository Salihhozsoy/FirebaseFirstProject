package com.example.firebasefirstproject.data.state

sealed class RegisterState{
    object Idle:RegisterState()
    object Loading:RegisterState()
    object Success:RegisterState()
    class Error(val throwable: Throwable?=null):RegisterState()
}
