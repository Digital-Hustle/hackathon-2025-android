package com.example.chat.domain.repository

import androidx.paging.PagingData
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

interface ChatRepository {


    fun getMessages(chatId: Int,userName: String): Flow<PagingData<MessageEntity>>

    suspend fun insertMessage(message: MessageEntity)

    suspend fun getChat(chatId: Int): ChatEntity

//    suspend fun  test()
    fun setChatId(chatId: Int)
    fun  setUserName(userName:String)
//    fun setChatInfo(chatId: Int, selfUserName: String)
    fun connect()
    fun sendMessage(message:String)
    fun unsubscribe()
    fun disconnect()
}