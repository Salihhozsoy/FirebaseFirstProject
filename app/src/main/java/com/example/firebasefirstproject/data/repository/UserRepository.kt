package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.state.UserListState

interface UserRepository {

    suspend fun insert(user: User)
    suspend fun getSignedUser(): User?
    suspend fun getAllUsers() :UserListState
    suspend fun sendPasswordResetEmail(email:String)
    suspend fun getUser() :User?
}