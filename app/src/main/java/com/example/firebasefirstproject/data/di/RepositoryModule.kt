package com.example.firebasefirstproject.data.di

import com.example.firebasefirstproject.data.repository.AuthRepository
import com.example.firebasefirstproject.data.repository.AuthRepositoryImpl
import com.example.firebasefirstproject.data.repository.UserRepository
import com.example.firebasefirstproject.data.repository.UserRepositoryImpl
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
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl
}