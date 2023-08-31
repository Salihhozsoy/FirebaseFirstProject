package com.example.firebasefirstproject.data.di

import com.example.firebasefirstproject.data.repository.AuthRepository
import com.example.firebasefirstproject.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository( authRepositoryImpl: AuthRepositoryImpl):AuthRepository = authRepositoryImpl
}