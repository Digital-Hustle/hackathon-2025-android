package com.example.chat.presentation.di

import com.example.chat.data.network.StompClient
import com.example.chat.data.repository.ChatMockRepository
import com.example.chat.data.repository.ChatRepositoryImpl
import com.example.chat.domain.repository.ChatRepository
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.dao.RemoteKeysDao
import com.example.chats_holder.data.network.ChatsApiService
import com.example.core.presentation.di.RepositoryFactory
import com.example.network.storage.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        messageDao: MessageDao,
        chatDao: ChatDao,
        apiService: ChatsApiService,
        chatDatabase: ChatDatabase,
        tokenManager: TokenManager,
        stompClient: StompClient,
        remoteKeysDao: RemoteKeysDao
    ): ChatRepository = RepositoryFactory<ChatRepository>().create(
        ChatRepositoryImpl(messageDao, chatDao, apiService, chatDatabase, tokenManager,stompClient,remoteKeysDao),
        ChatMockRepository(messageDao, chatDao)
    )

    @Provides
    @Singleton
    fun provideStompClient(okHttpClient: OkHttpClient, tokenManager: TokenManager,messageDao: MessageDao): StompClient =
        StompClient(okHttpClient, tokenManager,messageDao)
}