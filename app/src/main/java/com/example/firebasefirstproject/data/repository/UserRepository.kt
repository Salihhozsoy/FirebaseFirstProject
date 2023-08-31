package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.data.model.User

interface UserRepository {

    suspend fun insert(user: User)
    suspend fun getSignedUser(): User?

}