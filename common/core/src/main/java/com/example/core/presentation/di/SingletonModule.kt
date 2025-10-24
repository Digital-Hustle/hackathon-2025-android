package com.example.core.presentation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.data.UserStorageImpl
import com.example.core.domain.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.core.BuildConfig

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideUserStorage(@ApplicationContext context: Context): UserStorage =
        UserStorageImpl(context)


}







