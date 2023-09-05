package com.example.firebasefirstproject.data.repository

import android.net.Uri
import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.state.AllNewsState
import com.example.firebasefirstproject.data.state.UserListState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : NewsRepository {

    override suspend fun addNews(news: News, uris: List<Uri>) {
        fireStore.collection(Constants.NEWS).add(news).addOnSuccessListener {
            it.update(mapOf("id" to it.id))

            CoroutineScope(Dispatchers.IO).launch {
                uploadNewsPhoto(it.id, uris)
            }
        }
    }

    private suspend fun uploadNewsPhoto(newsId: String, uris: List<Uri>) {
        uris.forEach {
            val photoId =UUID.randomUUID().toString()
            firebaseStorage.reference.child(photoId).putFile(it).addOnCompleteListener{
                if(it.isSuccessful){
                    CoroutineScope(Dispatchers.IO).launch {
                        val downloadUrl =it.result.storage.downloadUrl.await()
                        fireStore.collection(Constants.NEWS).document(newsId).update("photos",FieldValue.arrayUnion(downloadUrl))
                    }
                }
            }
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