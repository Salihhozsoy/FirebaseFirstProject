package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.data.state.AuthState
import com.example.firebasefirstproject.data.state.RegisterState

interface AuthRepository {

    suspend fun login(email: String, password: String) :AuthState
    suspend fun register(email: String, password: String)
}