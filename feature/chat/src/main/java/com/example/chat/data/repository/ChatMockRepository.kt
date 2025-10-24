package com.example.chat.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.chat.domain.repository.ChatRepository
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatMockRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val chatDao: ChatDao,
) : ChatRepository {




    override fun getMessages(chatId: Int, userName: String): Flow<PagingData<MessageEntity>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
//            pagingSourceFactory = { MockMessagesPagingSource(userId, chatId) }
//        ).flow
    TODO("Not yet implemented")

    }


    override suspend fun insertMessage(message: MessageEntity) = messageDao.insertMessage(message)


    override suspend fun getChat(chatId: Int) = chatDao.getChatById(chatId)
    override fun setChatId(chatId: Int) {
        TODO("Not yet implemented")
    }

    override fun setUserName(userName: String) {
        TODO("Not yet implemented")
    }


    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun unsubscribe() {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

}
