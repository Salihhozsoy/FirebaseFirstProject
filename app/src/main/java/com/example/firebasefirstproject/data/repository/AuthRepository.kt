package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.data.state.AuthState
import com.example.firebasefirstproject.data.state.RegisterState
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun login(email: String, password: String) :AuthState

    suspend fun loginAnonymous():AuthState
    suspend fun register(email: String, password: String) :AuthResult
}