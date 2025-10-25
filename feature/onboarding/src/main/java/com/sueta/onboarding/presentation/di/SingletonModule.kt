package com.sueta.onboarding.presentation.di

import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.di.RepositoryFactory
import com.sueta.onboarding.data.network.OnboardingApiService
import com.sueta.onboarding.data.repository.OnboardingMockRepository
import com.sueta.onboarding.data.repository.OnboardingRepositoryImpl
import com.sueta.onboarding.domain.repository.OnboardingRepository
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
    fun provideOnboardingAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): OnboardingApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(OnboardingApiService::class.java)

    @Singleton
    @Provides
    fun provideOnboardingRepository(
        apiService: OnboardingApiService,
        userStorage: UserStorage
    ): OnboardingRepository = RepositoryFactory<OnboardingRepository>().create(
        OnboardingRepositoryImpl(apiService, userStorage), OnboardingMockRepository(userStorage)
    )



}