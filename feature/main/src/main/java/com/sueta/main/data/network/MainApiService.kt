package com.sueta.main.data.network


import com.sueta.main.domain.model.RouteRequest
import com.sueta.main.domain.model.RouteResponse
import retrofit2.Response
import retrofit2.http.Body

interface MainApiService {


//    @GET("/api/v1/profile/username/{username}")
//    suspend fun getProfile(@Path("username") username: String): Response<ProfileResponse>
//
//    @POST("/api/v1/profile/edit")
//    suspend fun editUserProfile(
//        @Body profile: ProfileRequest
//    ): Response<ProfileResponse>
//
//    @Multipart
//    @POST("/api/v1/profile/photo")
//    suspend fun uploadImage(
//        @Part image: MultipartBody.Part,
//    ):Response<ImageResponse>

    suspend fun createRoute(@Body route: RouteRequest): Response<RouteResponse>

    suspend fun getUserRoutes(userId: String): Response<List<RouteResponse>>

    suspend fun getTopTenRoutes(): Response<List<RouteResponse>>

    suspend fun getRouteById(id: String): Response<RouteResponse>


}