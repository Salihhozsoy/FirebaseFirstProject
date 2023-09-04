package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.data.state.AllNewsState

interface NewsRepository {
    suspend fun addNews(news: News)

    suspend fun getAllNews(userId: String?): AllNewsState

}