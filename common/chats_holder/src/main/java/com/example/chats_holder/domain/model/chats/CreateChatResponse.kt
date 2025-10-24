package com.example.chats_holder.domain.model.chats

import com.google.gson.annotations.SerializedName

data class CreateChatResponse(
    @SerializedName("data")
    val data: ChatResponse,
    @SerializedName("message")
    val message: String
)