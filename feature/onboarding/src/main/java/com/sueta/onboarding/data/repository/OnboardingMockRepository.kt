package com.sueta.onboarding.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.network.ApiResponse
import com.sueta.onboarding.domain.model.ProfileRequest
import com.sueta.onboarding.domain.model.ProfileResponse
import com.sueta.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnboardingMockRepository @Inject constructor(private val userStorage: UserStorage) :
    OnboardingRepository {


    override fun setProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = flow {
        emit(ApiResponse.Success(ProfileResponse(
            name = profile.name,
            surname = profile.surname,
            birthDate = profile.birthDate,
            interest = profile.interest
        )))
    }




}