package com.example.profile.domain.repository


import com.example.network.ApiResponse
import com.example.profile.domain.model.ImageResponse
import com.example.profile.domain.model.ProfileRequest
import com.example.profile.domain.model.ProfileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    fun getProfile(username: String?): Flow<ApiResponse<ProfileResponse>>

    fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>>

    fun uploadImage(
        image: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<ImageResponse>>



}