package com.example.firebasefirstproject.data.repository

import com.example.firebasefirstproject.News

interface NewsRepository {
    suspend fun addNews(news: News)
}