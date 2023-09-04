package com.example.firebasefirstproject.data.state

import com.example.firebasefirstproject.data.model.User

sealed class UserProfileState {
    object Idle : UserProfileState()
    object Loading : UserProfileState()
    class Error(var throwable: Throwable?) : UserProfileState()
    class Result(var user: User) : UserProfileState()
}
