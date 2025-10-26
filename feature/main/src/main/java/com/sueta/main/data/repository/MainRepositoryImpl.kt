package com.sueta.main.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.main.data.mapper.toRequest
import com.sueta.main.data.network.MainApiService
import com.sueta.main.domain.model.RouteRequest
import com.sueta.main.domain.model.RouteResponse
import com.sueta.main.domain.repository.MainRepository
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TravelStyle
import com.sueta.network.ApiResponse
import com.sueta.network.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import ru.dgis.sdk.coordinates.GeoPoint
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: MainApiService,
    private val userStorage: UserStorage
) : MainRepository {

    override fun createRoute(
        budget: BudgetType,
        startPoint: GeoPoint,
        endPoint: GeoPoint,
        style: TravelStyle,
        length: RouteType
    ): Flow<ApiResponse<RouteResponse>> = apiRequestFlow {
        apiService.createRoute(
            RouteRequest(
                budget = budget.value,
                categories = userStorage.getCategories().first()!!,
                endPoint = endPoint.toRequest(),
                startPoint = startPoint.toRequest(),
                style = style.value,
                length = length.value
            )
        )
    }

    override fun getRouteById(id: String): Flow<ApiResponse<RouteResponse>> = apiRequestFlow {
        apiService.getRouteById(id)
    }

    override fun getUserRoutes(): Flow<ApiResponse<List<RouteResponse>>> =
        apiRequestFlow {
            apiService.getUserRoutes(userStorage.getId().first()!!)
        }

    override fun getTopTenRoutes(): Flow<ApiResponse<List<RouteResponse>>> = apiRequestFlow {
        apiService.getTopTenRoutes()
    }


}