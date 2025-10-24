package com.example.chats_holder.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.local.dao.RemoteKeysDao
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.chats_holder.data.local.entities.RemoteKeys

@Database(
    entities = [ChatEntity::class, MessageEntity::class,RemoteKeys::class],
    version = 6, // увеличиваем версию
    exportSchema = false
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun remoteKeysDao():RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                )
                    .fallbackToDestructiveMigration() // если версия изменилась — пересоздать
                    .build().also { INSTANCE = it }
            }
        }
    }
}