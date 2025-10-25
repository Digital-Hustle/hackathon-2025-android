package com.sueta.auth.presentation.di

import android.content.Context
import com.sueta.auth.data.network.AuthApiService
import com.sueta.auth.data.repository.AuthMockRepository

import com.sueta.auth.data.auth.GoogleManager
import com.sueta.authtest.data.repository.AuthRepositoryImpl
import com.sueta.authtest.domain.repository.AuthRepository
import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.di.RepositoryFactory

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