package com.example.firebasefirstproject.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirstproject.Constants.NAME
import com.example.firebasefirstproject.Constants.SURNAME
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.state.ProfilePhotoUpdateState
import com.example.firebasefirstproject.data.state.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val _profilePhotoUpdateState: MutableSharedFlow<ProfilePhotoUpdateState>
) : ViewModel() {

    val profilePhotoUpdateState = _profilePhotoUpdateState.asSharedFlow()

    private val _userProfileState: MutableSharedFlow<UserProfileState> = MutableSharedFlow()
    val userProfileState: SharedFlow<UserProfileState> = _userProfileState


    fun getUser() {
        viewModelScope.launch {
            runCatching {
                userRepository.getUser()
                    ?.let { UserProfileState.Result(it) }?.let { _userProfileState.emit(it) }

            }.onFailure {
                _userProfileState.emit(UserProfileState.Error(it))
            }
        }
    }

    fun updateProfile(name: String, surname: String) {
        viewModelScope.launch {
            val map = mapOf(
                NAME to name,
                SURNAME to surname
            )
            userRepository.updateUser(map)
        }
    }

    fun uploadProfileImage(uri: Uri) {
        viewModelScope.launch {
            userRepository.uploadProfileImage(uri)
        }
    }
}