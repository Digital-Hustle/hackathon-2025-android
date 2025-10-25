    package com.sueta.onboarding.domain.model

    import com.google.gson.annotations.SerializedName

    data class ImageResponse(
        @SerializedName("message")
        val message:String,
        @SerializedName("data")
        val data:String?
    )
