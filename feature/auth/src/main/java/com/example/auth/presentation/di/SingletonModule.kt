package com.example.auth.presentation.di

import android.content.Context
import com.example.auth.data.network.AuthApiService
import com.example.auth.data.repository.AuthMockRepository

import com.example.auth.data.auth.GoogleManager
import com.example.authtest.data.repository.AuthRepositoryImpl
import com.example.authtest.domain.repository.AuthRepository
import com.example.core.domain.UserStorage
import com.example.core.presentation.di.RepositoryFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {


    @Singleton
    @Provides
    fun provideAuthAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): AuthApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)


    @Singleton
    @Provides
    fun provideGoogleManager(@ApplicationContext context: Context): GoogleManager =
        GoogleManager(context)


    @Singleton
    @Provides
    fun provideAuthRepository(
        authApiService: AuthApiService,
        storage: UserStorage,
        googleManager: GoogleManager
    ): AuthRepository = RepositoryFactory<AuthRepository>().create(
        AuthRepositoryImpl(
            authApiService,
            storage,
            googleManager
        ), AuthMockRepository(storage,googleManager)
    )
}