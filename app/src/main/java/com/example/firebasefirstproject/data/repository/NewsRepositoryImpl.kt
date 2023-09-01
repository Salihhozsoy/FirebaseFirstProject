package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.News
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore):NewsRepository {

    override suspend fun addNews(news: News) {
        fireStore.collection(Constants.NEWS).add(news).addOnSuccessListener {
            it.update(mapOf("id" to it.id))
        }
    }
}