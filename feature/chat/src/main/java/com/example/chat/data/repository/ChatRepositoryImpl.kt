package com.example.chat.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.chat.data.network.StompClient
import com.example.chat.domain.repository.ChatRepository
import com.example.chats_holder.data.local.ChatDatabase
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.dao.RemoteKeysDao
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.chats_holder.data.mediator.MessagesRemoteMediator
import com.example.chats_holder.data.network.ChatsApiService
import com.example.network.storage.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val chatDao: ChatDao,
    private val apiService: ChatsApiService,
    private val database: ChatDatabase,
    private val tokenManager: TokenManager,
    private val stompClient: StompClient,
    private val remoteKeysDao: RemoteKeysDao
) : ChatRepository {





    @OptIn(ExperimentalPagingApi::class)
    override fun getMessages(chatId: Int, userName: String): Flow<PagingData<MessageEntity>> {

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = MessagesRemoteMediator(
                chatId,
                userName,
                messageDao,
                apiService,
                database,remoteKeysDao
            ),
            pagingSourceFactory = { messageDao.getMessagesForChat(chatId) }
        ).flow
    }


    override suspend fun insertMessage(message: MessageEntity) = messageDao.insertMessage(message)


    override suspend fun getChat(chatId: Int) = chatDao.getChatById(chatId)


    override fun setChatId(chatId: Int) {
        stompClient.setChatId(chatId)
    }

    override fun setUserName(userName: String) {
        stompClient.setUserName(userName)
    }


    override fun connect() {
        stompClient.connect()
//        token?.let {
//            mLog("SUB")
//            stompClient.subscribeToChat(it) }
    }

    override fun sendMessage(message: String) {
//        mLog("ы", "TYT$token")
         stompClient.sendMessage(message)
    }

    override fun unsubscribe() {
        stompClient.unsubscribeToChat()
    }

    override fun disconnect() {
        stompClient.disconnect()
    }
}
