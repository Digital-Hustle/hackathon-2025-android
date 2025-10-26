package com.sueta.main.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.main.domain.model.EventResponse
import com.sueta.main.domain.model.PlaceResponse
import com.sueta.main.domain.model.RouteResponse
import com.sueta.main.domain.repository.MainRepository
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TravelStyle
import com.sueta.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dgis.sdk.coordinates.GeoPoint
import javax.inject.Inject

class MainMockRepository @Inject constructor(private val userStorage: UserStorage) :
    MainRepository {
//    override fun getProfile(username: String?): Flow<ApiResponse<ProfileResponse>> = flow {
//        emit(
//            ApiResponse.Success(
//                ProfileResponse(
//                    name = username ?: userStorage.getUsername().first()!!,
//                    image = null,
//                    surname = "Иванов",
//                    birthDate = "25.05.2005",
//                    interest = listOf("Природа","Культура")
//                )
//            )
//        )
//    }
//
//    override fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = flow {
//        emit(
//            ApiResponse.Success(
//                ProfileResponse(
//                    name = profile.name,
//                    surname = profile.surname,
//                    image = null,
//                    birthDate = profile.birthDate,
//                    interest = profile.interest
//                )
//            )
//        )
//    }
//
//    override fun uploadImage(
//        image: MultipartBody.Part,
////        description: RequestBody
//    ): Flow<ApiResponse<ImageResponse>> = flow {
//        emit(
//            ApiResponse.Success(
//                ImageResponse(
//                    "succes",
//                    ImageManager.getBase64FromMultipartBodyPart(image)
//                )
//            )
//        )
//    }


    override fun createRoute(
        budget: BudgetType,
        startPoint: GeoPoint,
        endPoint: GeoPoint,
        style: TravelStyle,
        length: RouteType
    ): Flow<ApiResponse<RouteResponse>> = flow {
        emit(
            ApiResponse.Success(
                RouteResponse(
                    id = "test_id",
                    places = listOf(
                        PlaceResponse(
                            address = "Пушкина",
                            events = listOf(
                                EventResponse(
                                    ageRestriction = 14,
                                    date = "25.05.2005",
                                    duration = "1.5 часа",
                                    id = "ididid",
                                    price = 1500,
                                    title = "Обрезание"
                                ),EventResponse(
                                    ageRestriction = 14,
                                    date = "25.05.2005",
                                    duration = "1.5 часа",
                                    id = "ididid",
                                    price = 1500,
                                    title = "Обрезание"
                                )
                            ),
                            id = "test_id2",
                            image = null,
                            latitude = 47.237394,
                            longitude = 39.712237,
                            title = "Кофейня"
                        ), PlaceResponse(
                            address = "Шушкина",
                            events = emptyList(),
                            id = "test_id2",
                            image = null,
                            latitude = 47.239868,
                            longitude = 39.700068,
                            title = "Кофейня"
                        )
                    )
                )
            )
        )
    }

    override fun getRouteById(id: String): Flow<ApiResponse<RouteResponse>> {
        TODO("Not yet implemented")
    }

    override fun getUserRoutes(): Flow<ApiResponse<List<RouteResponse>>> {
        TODO("Not yet implemented")
    }

    override fun getTopTenRoutes(): Flow<ApiResponse<List<RouteResponse>>> {
        TODO("Not yet implemented")
    }


}