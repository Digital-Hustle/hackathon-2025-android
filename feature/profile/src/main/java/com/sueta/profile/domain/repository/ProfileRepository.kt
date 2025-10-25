package com.sueta.profile.domain.repository


import com.sueta.network.ApiResponse
import com.sueta.profile.domain.model.ImageResponse
import com.sueta.profile.domain.model.ProfileRequest
import com.sueta.profile.domain.model.ProfileResponse
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