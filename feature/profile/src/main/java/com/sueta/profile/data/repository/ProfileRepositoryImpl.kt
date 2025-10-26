package com.sueta.profile.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.network.ApiResponse
import com.sueta.network.apiRequestFlow
import com.sueta.profile.data.network.ProfileApiService
import com.sueta.profile.domain.model.ImageResponse
import com.sueta.profile.domain.model.ProfileRequest
import com.sueta.profile.domain.model.ProfileResponse
import com.sueta.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ProfileApiService,
    private val userStorage: UserStorage
) : ProfileRepository {
    override fun getProfile(username: String?): Flow<ApiResponse<ProfileResponse>> =
        apiRequestFlow {
            apiService.getProfile(userStorage.getId().first()!!)

//            if (username != null) {
//                apiService.getProfile(username)
//            } else {
//                apiService.getProfile(userStorage.getUsername().first()!!)
//            }
        }

    override fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> =
        apiRequestFlow {
            apiService.editUserProfile(userStorage.getId().first()!!, profile)
        }

    override fun uploadImage(
        image: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<ImageResponse>> = apiRequestFlow {
        apiService.uploadImage(image)
    }


}