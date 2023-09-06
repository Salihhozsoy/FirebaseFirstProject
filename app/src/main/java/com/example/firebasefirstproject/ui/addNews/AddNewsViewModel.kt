package com.example.firebasefirstproject.ui.addNews

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.data.repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(private val newsRepository: NewsRepository, private val firebaseAuth: FirebaseAuth):ViewModel() {

    private val _newsPhotos: MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    fun addNews(title:String,content:String){
        viewModelScope.launch {
            val news =News(title=title, content = content, editorId = firebaseAuth.currentUser?.uid)
            newsRepository.addNews(news,_newsPhotos.first())
        }
    }

    fun setNewsPhotos(uris:List<Uri>){
        viewModelScope.launch {
            _newsPhotos.emit(uris)
        }
    }
}