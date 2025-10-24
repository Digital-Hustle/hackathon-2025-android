package com.example.chats_holder.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: Int,
    val title: String,
//    val lastMessage: String?,
//    val timestamp: Long
    val photo:String? = null
)
