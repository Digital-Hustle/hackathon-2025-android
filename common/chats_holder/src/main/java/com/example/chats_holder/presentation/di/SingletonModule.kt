package com.example.chats_holder.presentation.di

import android.content.Context
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.dao.RemoteKeysDao

import com.example.chats_holder.data.network.ChatsApiService


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import javax.inject.Singleton

//val Context.chatsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {


    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return ChatDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideChatDao(chatDatabase: ChatDatabase): ChatDao {
        return chatDatabase.chatDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(chatDatabase: ChatDatabase): MessageDao {
        return chatDatabase.messageDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(chatDatabase: ChatDatabase): RemoteKeysDao {
        return chatDatabase.remoteKeysDao()
    }


    @Singleton
    @Provides
    fun provideChatsAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): ChatsApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(ChatsApiService::class.java)
}







