package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firebaseFireStore: FirebaseFirestore,private val firebaseAuth: FirebaseAuth):UserRepository {
    override suspend fun insert(user: User) {
        firebaseFireStore.collection(Constants.USERS).document(user.id.toString()).set(user)
    }

    override suspend fun getSignedUser(): User? {
       return firebaseFireStore.collection(Constants.USERS).document(firebaseAuth.currentUser?.uid.orEmpty()).get().await().toObject(User::class.java)
    }
}