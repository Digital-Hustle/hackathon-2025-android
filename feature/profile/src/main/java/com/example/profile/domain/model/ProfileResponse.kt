package com.example.profile.domain.model
import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("age")
    var age: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("sex")
    var sex: Int?,
    @SerializedName("photo")
    var image:String?,
)

