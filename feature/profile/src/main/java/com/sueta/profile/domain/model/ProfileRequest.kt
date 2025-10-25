package com.sueta.profile.domain.model
import com.google.gson.annotations.SerializedName


data class ProfileRequest(
    @SerializedName("age")
    var age: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("sex")
    var sex: Int?,
)

