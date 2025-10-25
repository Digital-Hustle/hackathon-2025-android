package com.sueta.auth.domain.models

import com.google.gson.annotations.SerializedName

data class GoogleAuthRequest(
    @SerializedName("token")
    val token: String
)
