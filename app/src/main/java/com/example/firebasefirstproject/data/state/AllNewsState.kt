package com.example.firebasefirstproject.data.state

import com.example.firebasefirstproject.News

sealed class AllNewsState{
    object Idle : AllNewsState()
    class Result(val news:List<News>) : AllNewsState()
    class Error(val throwable: Throwable? =null) : AllNewsState()
    object Empty : AllNewsState()
    object Loading : AllNewsState()
}
