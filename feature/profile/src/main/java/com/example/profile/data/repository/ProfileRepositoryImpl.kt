package com.example.profile.data.repository

import com.example.core.domain.UserStorage
import com.example.network.ApiResponse
import com.example.network.apiRequestFlow
import com.example.profile.data.network.ProfileApiService
import com.example.profile.domain.model.ImageResponse
import com.example.profile.domain.model.ProfileRequest
import com.example.profile.domain.model.ProfileResponse
import com.example.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ProfileApiService,
    private val userStorage: UserStorage
) : ProfileRepository {
    override fun getProfile(username: String?): Flow<ApiResponse<ProfileResponse>> = apiRequestFlow {
        if (username != null) {
            apiService.getProfile(username)
        } else {
            apiService.getProfile(userStorage.getUsername().first()!!)
        }
    }

    override fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = apiRequestFlow {
        apiService.editUserProfile(profile)
    }

    override fun uploadImage(
        image: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<ImageResponse>> = apiRequestFlow {
        apiService.uploadImage(image)
    }




}