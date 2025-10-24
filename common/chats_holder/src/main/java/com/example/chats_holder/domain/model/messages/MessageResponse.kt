package com.example.chats_holder.domain.model.messages

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("senderName")
    val senderName: String,
    @SerializedName("messageText")
    val text: String,
    @SerializedName("createdAt")
    val timestamp: String,
    )
