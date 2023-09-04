package com.example.firebasefirstproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    private val _userListState:MutableStateFlow<UserListState> = MutableStateFlow(UserListState.Idle)
    val userListState:StateFlow<UserListState> =_userListState

     fun getUsers(){
        viewModelScope.launch {
            kotlin.runCatching {
                _userListState.value =UserListState.Loading
                _userListState.value =userRepository.getAllUsers()
            }.onFailure {
                _userListState.value =UserListState.Error(it)
            }
        }
    }
}