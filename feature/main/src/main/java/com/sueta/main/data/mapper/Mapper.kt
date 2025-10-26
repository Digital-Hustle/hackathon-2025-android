package com.sueta.main.data.mapper

import android.util.Size
import com.sueta.core.mLog
import com.sueta.main.R
import com.sueta.main.domain.model.EventResponse
import com.sueta.main.domain.model.PlaceResponse
import com.sueta.main.domain.model.PointRequest
import com.sueta.main.domain.model.RouteResponse
import com.sueta.main.presentation.model.Event
import com.sueta.main.presentation.model.Place
import com.sueta.main.presentation.model.Route
import ru.dgis.sdk.Context
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.DirectoryObject
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.imageFromResource

fun DirectoryObject.toStringAddress(): String {
    mLog("TITLE", this.address?.components?.toString() ?: "пустая хуйня")
    if (this.address?.components?.isEmpty() == true) return "_"
    val adressStreet = this.address?.components[0]?.asStreetAddress
    return adressStreet?.street + " " + adressStreet?.number
}

fun DirectoryObject.toGeoPoint(): GeoPoint {
    val geoPointWithElevation = this.markerPosition
    return GeoPoint(
        latitude = geoPointWithElevation?.latitude?.value ?: 0.0,
        longitude = geoPointWithElevation?.longitude?.value ?: 0.0
    )
}


fun PointRequest.toGeoPoint(): GeoPoint = GeoPoint(
    latitude = lat,
    longitude = lon
)

fun GeoPoint.toRequest(): PointRequest = PointRequest(
    lat = latitude.value,
    lon = longitude.value
)

fun RouteResponse.toRoute(): Route = Route(
    id = this.id,
    places = this.places.map { it.toPlace() }
)


fun PlaceResponse.toPlace(): Place = Place(
    address = this.address,
    events = this.events.map { it.toEvent() },
    id = this.id,
    image = this.image,
    point = GeoPoint(this.latitude, this.longitude),
    title = this.title,
)


fun EventResponse.toEvent(): Event = Event(
    ageRestriction = this.ageRestriction,
    date = this.date,
    duration = this.duration,
    id = this.id,
    price = this.price,
    title = this.title
)


fun Place.toGeoPoint(): GeoPoint = GeoPoint(
    latitude = this.point.latitude.value,
    longitude = this.point.longitude.value
)


fun Place.toMarker(sdkContext: Context): Marker = Marker(
    MarkerOptions(
        position = GeoPointWithElevation(
            latitude = this.point.latitude.value,
            longitude = this.point.longitude.value
        ), icon = imageFromResource(sdkContext, R.drawable.loc_foreground,size = Size(
            100,100
        )
        )
    )
)


fun List<RouteResponse>.toRoutes():List<Route>{
    return this.map { it.toRoute() }
}

fun List<Place>.toGeoPoints(): List<GeoPoint> {
    return this.map { it.toGeoPoint() }
}

fun List<Place>.toMarkers(sdkContext: Context): List<Marker> {
    return this.map { it.toMarker(sdkContext) }
}

fun Route.toGeoPoints(): List<GeoPoint> {
    return this.places.toGeoPoints()
}


