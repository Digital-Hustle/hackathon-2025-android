package com.example.chats.domain.repository

import androidx.paging.PagingData
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.domain.model.chats.CreateChatResponse
import com.example.chats_holder.domain.model.chats.SearchUsersResponse
import com.example.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {

    fun createChat(userId: Int): Flow<ApiResponse<CreateChatResponse>>

    fun getChats(): Flow<PagingData<ChatEntity>>

    fun searchUsers(
        query: String,
        page: Int,
        size: Int
    ): Flow<ApiResponse<SearchUsersResponse>>

    suspend fun insertChat(chat: ChatEntity)

}