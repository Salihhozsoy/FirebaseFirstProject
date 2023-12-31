package com.example.firebasefirstproject.data.repository

import android.net.Uri
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.data.state.AllNewsState

interface NewsRepository {
    suspend fun addNews(news: News,uris:List<Uri>)

    suspend fun getAllNews(userId: String?): AllNewsState

}