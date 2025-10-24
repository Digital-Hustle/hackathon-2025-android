package com.example.profile.data.network


import com.example.profile.domain.model.ImageResponse
import com.example.profile.domain.model.ProfileRequest
import com.example.profile.domain.model.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileApiService {


    @GET("/api/v1/profile/username/{username}")
    suspend fun getProfile(@Path("username") username: String): Response<ProfileResponse>

    @POST("/api/v1/profile/edit")
    suspend fun editUserProfile(
        @Body profile: ProfileRequest
    ): Response<ProfileResponse>

    @Multipart
    @POST("/api/v1/profile/photo")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    ):Response<ImageResponse>


}