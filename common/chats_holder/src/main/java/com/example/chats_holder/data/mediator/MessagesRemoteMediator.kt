package com.example.chats_holder.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.dao.RemoteKeysDao
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.chats_holder.data.local.entities.RemoteKeys
import com.example.chats_holder.data.mapper.toMessageEntities
import com.example.chats_holder.data.network.ChatsApiService
import com.example.core.mLog
import com.example.network.ApiResponse
import com.example.network.apiRequestFlow

@OptIn(ExperimentalPagingApi::class)
class MessagesRemoteMediator(
    private val chatId: Int,
    private val userName: String,
    private val messageDao: MessageDao,
    private val apiService: ChatsApiService,
    private val database: ChatDatabase,
    private val remoteKeysDao: RemoteKeysDao
) : RemoteMediator<Int, MessageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    // Начинаем с нуля
                    0
                }

                LoadType.APPEND -> {
                    val remoteKeys = remoteKeysDao.remoteKeysByChatId(chatId)
                    remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.PREPEND -> {
                    // Вверх не грузим
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            var endOfPagination = false
            mLog("mLogMEDIATOR",page.toString())
            apiRequestFlow { apiService.getMessages(chatId, page, state.config.pageSize) }
                .collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val messages = response.data.data
                            endOfPagination = messages.isEmpty()

                            database.withTransaction {
                                if (loadType == LoadType.REFRESH) {
                                    messageDao.clearAllMessages()
                                }
                                mLog("mediatorMessage", "$userName ТУТ$messages")

                                messageDao.insertMessages(
                                    messages.toMessageEntities(
                                        chatId,
                                        selfUserName = userName

                                    )
                                )
                                val nextPage = if (endOfPagination) null else page + 1
                                remoteKeysDao.insertOrReplace(
                                    RemoteKeys(chatId = chatId, nextPage = nextPage)
                                )

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
            mLog("mLogMEDIATORERR",e.message.toString())

            MediatorResult.Error(e)
        }
    }
}
