package com.sueta.profile.presentation.di

import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.di.RepositoryFactory
import com.sueta.profile.data.network.ProfileApiService
import com.sueta.profile.data.repository.ProfileMockRepository
import com.sueta.profile.data.repository.ProfileRepositoryImpl
import com.sueta.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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