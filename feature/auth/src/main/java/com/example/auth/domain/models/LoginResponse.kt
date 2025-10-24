package com.example.auth.domain.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("username")
    val username:String,
    @SerializedName("accessToken")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken:String

)