package com.example.auth.domain.models

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("username")
    val username:String,
    )
