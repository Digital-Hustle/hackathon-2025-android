package com.sueta.profile.domain.model

import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("name")
    var name: String?,
    @SerializedName("surname")
    var surname: String?,
    @SerializedName("birth_date")
    var birthDate: String?,
    @SerializedName("interests")
    val interest: List<String>?,
    @SerializedName("photo")
    var image: String?,
)

