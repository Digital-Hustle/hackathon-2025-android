package com.example.chats_holder.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chats_holder.data.local.entities.ChatEntity

@Dao
interface ChatDao {
//    @Query("SELECT * FROM chats ORDER BY timestamp DESC")

    @Query("SELECT * FROM chats")
    fun getChatsPagingSource(): PagingSource<Int, ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chats: List<ChatEntity>)

    @Query("DELETE FROM chats")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Query("SELECT * FROM chats WHERE id = :chatId")
    suspend fun getChatById(chatId:Int):ChatEntity

}
