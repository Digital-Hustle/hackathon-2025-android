package com.example.chats_holder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chats_holder.data.local.entities.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE chatId = :chatId")
    suspend fun remoteKeysByChatId(chatId: Int): RemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeys: RemoteKeys)

    @Query("DELETE FROM remote_keys WHERE chatId = :chatId")
    suspend fun deleteByChatId(chatId: Int)
}