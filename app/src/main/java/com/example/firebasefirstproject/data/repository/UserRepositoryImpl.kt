package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.state.UserListState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override suspend fun insert(user: User) {
        firebaseFireStore.collection(Constants.USERS).document(user.id.toString()).set(user)
    }

    override suspend fun getSignedUser(): User? {
        return firebaseFireStore.collection(Constants.USERS).document(firebaseAuth.currentUser?.uid.orEmpty()).get().await().toObject(User::class.java)
    }

    override suspend fun getAllUsers(): UserListState {
        val querySnapshot = firebaseFireStore.collection(Constants.USERS).get().await()
        return if (querySnapshot.isEmpty) UserListState.Empty
        else UserListState.Result(querySnapshot.toObjects(User::class.java))
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
}