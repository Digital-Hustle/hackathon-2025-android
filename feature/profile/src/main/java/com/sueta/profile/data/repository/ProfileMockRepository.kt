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
                    sex = 0,
                    age = 20,
                    description = "20 лет. Разработчик из Ростова-на-Дону",
                    image = null
                )
            )
        )
    }

    override fun editProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = flow {
        emit(ApiResponse.Success(ProfileResponse(
            age = profile.age,
            description = profile.description,
            name = profile.name,
            sex = profile.sex,
            image = null
        )))
    }

    override fun uploadImage(
        image: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<ImageResponse>> = flow {
        emit(ApiResponse.Success(ImageResponse("succes", ImageManager.getBase64FromMultipartBodyPart(image))))
    }



}