package com.example.firebasefirstproject.ui.login1.forgotPassword

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { listener ->
            if (listener.isSuccessful)
                println("reset mail gönderildi")
        }
    }
}