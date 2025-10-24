package com.example.chats_holder.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.mapper.toChatEntities
import com.example.chats_holder.data.network.ChatsApiService
import com.example.core.mLog
import com.example.network.ApiResponse
import com.example.network.apiRequestFlow

@OptIn(ExperimentalPagingApi::class)
class ChatsRemoteMediator(
    private val chatDao: ChatDao,
    private val apiService: ChatsApiService,
    private val database: ChatDatabase
) : RemoteMediator<Int, ChatEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ChatEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastChat = state.lastItemOrNull()
                    if (lastChat == null) 0 else (state.pages.size + 1)
                }
            }

            var endOfPagination = false

            apiRequestFlow { apiService.getChats(page, state.config.pageSize) }
                .collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val chats = response.data.data
                            endOfPagination = chats.isEmpty()

                            database.withTransaction {
                                if (loadType == LoadType.REFRESH) {
                                    chatDao.clearAll()
                                }
                                mLog("mediator", "ТУТ$chats")
                                chatDao.insertChats(chats.toChatEntities())
                            }
                        }

                        is ApiResponse.Failure -> {
                            MediatorResult.Error(Exception("Ошибка ${response.code}: ${response.errorMessage}"))
                        }

                        else -> {} // Ничего не делаем для Loading
                    }
                }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
