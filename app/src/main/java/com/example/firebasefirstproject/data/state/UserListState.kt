package com.example.firebasefirstproject.data.state

import com.example.firebasefirstproject.data.model.User

sealed class UserListState {
    object Idle : UserListState()
    class Result(val users:List<User>) : UserListState()
    class Error(val throwable: Throwable? =null) : UserListState()
    object Empty : UserListState()
    object Loading : UserListState()
}
