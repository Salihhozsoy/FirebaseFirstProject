package com.example.firebasefirstproject.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.state.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    private val _userProfileState : MutableSharedFlow<UserProfileState> = MutableSharedFlow()
    val userProfileState: SharedFlow<UserProfileState> = _userProfileState

    fun getUser(){
        viewModelScope.launch {
            runCatching {
                userRepository.getUser()
                    ?.let { UserProfileState.Result(it) }?.let { _userProfileState.emit(it) }

            }.onFailure {
                    _userProfileState.emit(UserProfileState.Error(it))
            }
        }
    }
}