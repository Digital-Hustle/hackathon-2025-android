package com.sueta.onboarding.data.repository

import com.sueta.core.domain.UserStorage
import com.sueta.network.ApiResponse
import com.sueta.network.apiRequestFlow
import com.sueta.onboarding.data.network.OnboardingApiService
import com.sueta.onboarding.domain.model.ProfileRequest
import com.sueta.onboarding.domain.model.ProfileResponse
import com.sueta.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val apiService: OnboardingApiService,
    private val userStorage: UserStorage
) : OnboardingRepository {
    override fun setProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>> = apiRequestFlow {
        apiService.setUserProfile(profile)
    }
}