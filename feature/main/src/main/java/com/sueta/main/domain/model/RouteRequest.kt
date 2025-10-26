package com.sueta.main.domain.model

import com.google.gson.annotations.SerializedName

data class RouteRequest(
    @SerializedName("budget")
    val budget: String,
    @SerializedName("categories")
    val categories: Set<String>,
    @SerializedName("endPoint")
    val endPoint: PointRequest,
    @SerializedName("startPoint")
    val startPoint: PointRequest,
    @SerializedName("style")
    val style: String,
    @SerializedName("routeLength")
    val length: String
)

data class PointRequest(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)




