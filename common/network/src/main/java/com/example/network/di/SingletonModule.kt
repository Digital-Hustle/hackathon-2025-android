package com.example.network.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.BuildConfig
import com.example.network.storage.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.example.network.AuthInterceptor
import com.example.network.AuthAuthenticator
import com.example.network.Interface.RefreshApiService
import com.example.network.presentation.ImageManager

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_data_store")

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {


    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())

//    BuildConfig.BACKEND_URL todo

    @Singleton
    @Provides
    fun provideRefreshAPIService(okHttpClient: OkHttpClient,retrofit: Retrofit.Builder): RefreshApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(RefreshApiService::class.java)

    @Singleton
    @Provides
    fun provideImageManager(@ApplicationContext context: Context)= ImageManager(context)



}