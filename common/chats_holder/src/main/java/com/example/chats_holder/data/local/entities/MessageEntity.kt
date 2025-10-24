package com.example.chats_holder.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("chatId")] // ускоряет JOIN-операции
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chatId: Int,            // связь с чатами
    val senderName: String,
    val content: String,        // текст сообщения
    val timestamp: Long,        // когда отправлено
    val isMyMessage: Boolean    // для UI (лево/право)
)