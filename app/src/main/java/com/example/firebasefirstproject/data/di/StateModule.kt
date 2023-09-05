package com.example.firebasefirstproject.data.di

import com.example.firebasefirstproject.data.state.ProfilePhotoUpdateState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateModule {

    @Provides
    @Singleton
    fun provideProfilePhotoUpdateState(): ProfilePhotoUpdateState = ProfilePhotoUpdateState.Idle

    @Provides
    @Singleton
    fun provideMutableProfilePhotoUpdateState(): MutableSharedFlow<ProfilePhotoUpdateState> = MutableSharedFlow()
}