package com.example.firebasefirstproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    private val _user: MutableSharedFlow<User> = MutableSharedFlow()
    val user:SharedFlow<User> =_user

    init {
        viewModelScope.launch {
            userRepository.getSignedUser()?.let {
                _user.emit(it)
            }?: kotlin.run {
                //go login
            }
        }
    }
}