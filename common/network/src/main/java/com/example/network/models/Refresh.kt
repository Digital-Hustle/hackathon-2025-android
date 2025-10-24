package com.example.network.models

import com.google.gson.annotations.SerializedName

data class Refresh(
    @SerializedName("refreshToken")
    val token:String?
)
