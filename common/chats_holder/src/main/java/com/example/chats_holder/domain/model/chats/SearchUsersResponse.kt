package com.example.chats_holder.domain.model.chats

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("data")
    val data: List<UserResponse>,
    @SerializedName("message")
    val message: String
)