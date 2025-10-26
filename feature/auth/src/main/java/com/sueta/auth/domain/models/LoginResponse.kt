package com.sueta.auth.domain.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id:String,
    @SerializedName("username")
    val username:String,
    @SerializedName("accessToken")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken:String

)