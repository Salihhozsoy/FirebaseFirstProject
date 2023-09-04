package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.state.AllNewsState
import com.example.firebasefirstproject.data.state.UserListState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore) : NewsRepository {

    override suspend fun addNews(news: News) {
        fireStore.collection(Constants.NEWS).add(news).addOnSuccessListener {
            it.update(mapOf("id" to it.id))
        }
    }

    override suspend fun getAllNews(userId: String?): AllNewsState {

        val querySnapshot: QuerySnapshot = if (userId == null)
            fireStore.collection(Constants.NEWS).get().await()
        else
            fireStore.collection(Constants.NEWS).whereEqualTo(Constants.EDITOR_ID, userId).get()
                .await()

        return if (querySnapshot.isEmpty) AllNewsState.Empty
        else AllNewsState.Result(querySnapshot.toObjects(News::class.java))
    }
}