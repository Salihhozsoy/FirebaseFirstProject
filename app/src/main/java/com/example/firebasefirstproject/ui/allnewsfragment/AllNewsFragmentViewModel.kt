package com.example.firebasefirstproject.ui.allnewsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.repository.NewsRepository
import com.example.firebasefirstproject.data.state.AllNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNewsFragmentViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _allNewsState: MutableStateFlow<AllNewsState> = MutableStateFlow(AllNewsState.Idle)
    val allNewsState: StateFlow<AllNewsState> = _allNewsState

    fun getAllNews(userId: String?) {
        viewModelScope.launch {
            kotlin.runCatching {
                _allNewsState.value = AllNewsState.Loading
                _allNewsState.value = newsRepository.getAllNews(userId)

            }.onFailure {
                _allNewsState.value = AllNewsState.Error(it)
            }
        }
    }
}