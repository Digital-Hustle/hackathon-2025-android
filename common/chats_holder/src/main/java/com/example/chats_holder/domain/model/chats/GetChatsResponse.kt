package com.example.chats_holder.domain.model.chats

import com.google.gson.annotations.SerializedName

data class GetChatsResponse(
    @SerializedName("data")
    val data: List<ChatResponse>,
    @SerializedName("message")
    val message: String
)