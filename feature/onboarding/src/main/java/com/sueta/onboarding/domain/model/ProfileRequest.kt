package com.sueta.onboarding.domain.model
import com.google.gson.annotations.SerializedName


data class ProfileRequest(
    @SerializedName("birth_date")
    var birthDate: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("surname")
    var surname: String?,
    @SerializedName("interests")
    val interest:List<String>?
)

