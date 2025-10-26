package com.sueta.main.domain.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("events")
    val events: List<EventResponse>,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String
)

data class EventResponse(
    @SerializedName("ageRestriction")
    val ageRestriction: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("title")
    val title: String
)


