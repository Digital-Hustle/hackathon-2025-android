package com.example.chats_holder.domain.model.chats

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?
)