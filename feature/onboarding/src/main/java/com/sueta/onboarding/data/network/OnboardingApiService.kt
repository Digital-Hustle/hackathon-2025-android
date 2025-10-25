package com.sueta.onboarding.data.network


import com.sueta.onboarding.domain.model.ProfileRequest
import com.sueta.onboarding.domain.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OnboardingApiService {
    @POST("/api/v1/profile/edit")
    suspend fun setUserProfile(
        @Body profile: ProfileRequest
    ): Response<ProfileResponse>
}