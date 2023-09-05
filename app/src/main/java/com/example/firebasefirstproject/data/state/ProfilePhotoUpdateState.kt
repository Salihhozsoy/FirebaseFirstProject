package com.example.firebasefirstproject.data.state

sealed class ProfilePhotoUpdateState {
    object Idle : ProfilePhotoUpdateState()
    object Loading : ProfilePhotoUpdateState()
    object Success : ProfilePhotoUpdateState()
    class Progress(val pr: Int) : ProfilePhotoUpdateState()
    class Error(val throwable: Throwable? = null) : ProfilePhotoUpdateState()

}
