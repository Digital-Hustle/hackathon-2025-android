package com.sueta.onboarding.domain.repository


import com.sueta.network.ApiResponse
import com.sueta.onboarding.domain.model.ProfileRequest
import com.sueta.onboarding.domain.model.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    fun setProfile(profile: ProfileRequest): Flow<ApiResponse<ProfileResponse>>
}