package com.sueta.main.domain.repository


import com.sueta.main.domain.model.RouteResponse
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TravelStyle
import com.sueta.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import ru.dgis.sdk.coordinates.GeoPoint

interface MainRepository {

    fun createRoute(
        budget: BudgetType,
        startPoint: GeoPoint,
        endPoint: GeoPoint,
        style: TravelStyle,
        length: RouteType
    ): Flow<ApiResponse<RouteResponse>>

    fun getRouteById(id: String): Flow<ApiResponse<RouteResponse>>

    fun getUserRoutes(): Flow<ApiResponse<List<RouteResponse>>>

    fun getTopTenRoutes(): Flow<ApiResponse<List<RouteResponse>>>
}