package com.sueta.profile.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.network.ApiResponse
import com.sueta.network.presentation.ImageManager
import com.sueta.profile.domain.model.ImageResponse
import com.sueta.profile.domain.model.ProfileRequest
import com.sueta.profile.domain.model.ProfileResponse
import com.sueta.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileMockRepository @Inject constructor(private val userStorage: UserStorage) :
    ProfileRepository {
    override fun getProfile(username: String?): Flow<ApiResponse<ProfileResponse>> = flow {
        emit(
            ApiResponse.Success(
                ProfileResponse(
                    name = username ?: userStorage.getUsername().first()!!,
                    image = null,
                    surname = "Иванов",
                    birthDate = "25.05.2005",
                    interest = listOf("Природа","Культура")
                )
            )
        )
    }

    override fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = flow {
        emit(
            ApiResponse.Success(
                ProfileResponse(
                    name = profile.name,
                    surname = profile.surname,
                    image = null,
                    birthDate = profile.birthDate,
                    interest = profile.interest
                )
            )
        )
    }

    override fun uploadImage(
        image: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<ImageResponse>> = flow {
        emit(
            ApiResponse.Success(
                ImageResponse(
                    "succes",
                    ImageManager.getBase64FromMultipartBodyPart(image)
                )
            )
        )
    }


}