package com.example.chats_holder.domain.model.messages

import com.example.chats_holder.domain.model.chats.ChatResponse
import com.google.gson.annotations.SerializedName

data class GetMessagesResponse(
    @SerializedName("data")
    val data: List<MessageResponse>,
    @SerializedName("message")
    val message: String
)
