package com.example.step5app.di

import com.example.step5app.data.repositories.AuthRepositoryImpl
import com.example.step5app.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}