package com.example.auth.domain.models

import com.google.gson.annotations.SerializedName

data class GoogleAuthRequest(
    @SerializedName("token")
    val token: String
)
