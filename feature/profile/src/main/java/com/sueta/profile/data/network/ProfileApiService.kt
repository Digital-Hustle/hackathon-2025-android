package com.sueta.profile.data.network


import com.sueta.profile.domain.model.ImageResponse
import com.sueta.profile.domain.model.ProfileRequest
import com.sueta.profile.domain.model.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileApiService {


    @GET("profile/api/v1/profile/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<ProfileResponse>

    @PUT("profile/api/v1/profile/{id}")
    suspend fun editUserProfile(
        @Path("id") id:String,
        @Body profile: ProfileRequest
    ): Response<ProfileResponse>

    @Multipart
    @POST("profile/api/v1/profile/photo")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    ):Response<ImageResponse>


}