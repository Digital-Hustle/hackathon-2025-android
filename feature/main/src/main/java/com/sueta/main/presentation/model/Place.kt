package com.sueta.main.presentation.model

import ru.dgis.sdk.coordinates.GeoPoint


data class Place(
    val address: String,
    val events: List<Event>,
    val id: String,
    val image: String?,
    val point: GeoPoint,
    val title: String
)

data class Event(
    val ageRestriction: Int,
    val date: String,
    val duration: String,
    val id: String,
    val price: Int,
    val title: String
)


