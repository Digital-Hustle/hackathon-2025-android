package com.sueta.onboarding.domain.model
import com.google.gson.annotations.SerializedName


data class ProfileRequest(
    @SerializedName("dateOfBirth")
    var birthDate: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("surname")
    var surname: String?,
    @SerializedName("interests")
    val interest:List<String>?,
    @SerializedName("image")
    val image:String?
)

