package com.example.chats.presentation.di

import com.example.chats.data.repository.ChatsMockRepository
import com.example.chats.domain.repository.ChatsRepository
import com.example.chats.data.repository.ChatsRepositoryImpl
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.network.ChatsApiService
import com.example.core.presentation.di.RepositoryFactory
//import com.example.chats.data.network.ChatsApiService
//import com.example.chats.data.repository.ChatsMockRepository
//import com.example.chats.data.repository.ChatsRepositoryImpl
//import com.example.chats.domain.repository.ChatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        chatDao: ChatDao,
        apiService: ChatsApiService,
        chatDatabase: ChatDatabase
    ): ChatsRepository = RepositoryFactory<ChatsRepository>().create(
        ChatsRepositoryImpl(chatDao, apiService, chatDatabase), ChatsMockRepository(chatDao)
    )

}