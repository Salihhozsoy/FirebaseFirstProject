package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.data.state.AuthState
import com.example.firebasefirstproject.data.state.RegisterState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthState {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return if (authResult.user == null) AuthState.UserNotFound else AuthState.Success
    }

    override suspend fun register(email: String, password: String) {
      firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
}