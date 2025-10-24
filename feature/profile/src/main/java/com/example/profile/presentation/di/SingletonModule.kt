package com.example.profile.presentation.di

import android.content.Context
import com.example.core.domain.UserStorage
import com.example.core.presentation.di.RepositoryFactory
import com.example.network.presentation.ImageManager
import com.example.profile.data.network.ProfileApiService
import com.example.profile.data.repository.ProfileMockRepository
import com.example.profile.data.repository.ProfileRepositoryImpl
import com.example.profile.domain.repository.ProfileRepository
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
    fun provideProfileAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): ProfileApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(ProfileApiService::class.java)

    @Singleton
    @Provides
    fun provideProfileRepository(
        apiService: ProfileApiService,
        userStorage: UserStorage
    ): ProfileRepository = RepositoryFactory<ProfileRepository>().create(
        ProfileRepositoryImpl(apiService,userStorage), ProfileMockRepository(userStorage)
    )



}