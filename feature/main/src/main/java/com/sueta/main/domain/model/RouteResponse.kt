package com.sueta.main.domain.model

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("places")
    val places:List<PlaceResponse>
)
