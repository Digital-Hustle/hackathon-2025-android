package com.example.chats.data.mapper

import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.domain.model.chats.ChatResponse
import com.example.chats_holder.domain.model.chats.UserResponse


fun UserResponse.toChat(): ChatEntity = ChatEntity(
    id = id,
    title = name?:"",
    photo = photo
//    name = name,
//    userId = id,
////    image = photo
//    image = photo?.let { ImageManager.base64toBitmap(it) }
)

fun ChatResponse.toChat(): ChatEntity = ChatEntity(
    id = id,
    title = name?:"",
    photo = photo
)

fun List<UserResponse>.toChatsFromUsers(): List<ChatEntity> {
    return this.map { it.toChat() }
}


