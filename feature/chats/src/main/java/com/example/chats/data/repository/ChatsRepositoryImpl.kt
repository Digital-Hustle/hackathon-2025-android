package com.example.chats.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.chats.domain.repository.ChatsRepository
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.mediator.ChatsRemoteMediator
import com.example.chats_holder.data.network.ChatsApiService
import com.example.chats_holder.domain.model.chats.CreateChatResponse
import com.example.chats_holder.domain.model.chats.SearchUsersResponse
import com.example.network.ApiResponse
import com.example.network.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao,
    private val apiService: ChatsApiService,
    private val database: ChatDatabase
) : ChatsRepository {




    @OptIn(ExperimentalPagingApi::class)
    override fun getChats(): Flow<PagingData<ChatEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = ChatsRemoteMediator(chatDao, apiService, database),
            pagingSourceFactory = { chatDao.getChatsPagingSource() }
        ).flow
    }

    override fun searchUsers(
        query: String,
        page: Int,
        size: Int
    ): Flow<ApiResponse<SearchUsersResponse>> = apiRequestFlow {
        apiService.searchUsers(query, page, size)
    }

    override suspend fun insertChat(chat: ChatEntity){
        chatDao.insertChat(chat)
    }

    override fun createChat(userId: Int): Flow<ApiResponse<CreateChatResponse>> {
        return apiRequestFlow {
            apiService.createChat(userId)
        }
    }
}
