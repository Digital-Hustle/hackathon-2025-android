package com.sueta.onboarding.data.network


import com.sueta.onboarding.domain.model.ProfileRequest
import com.sueta.onboarding.domain.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OnboardingApiService {


    @POST("profile/api/v1/profile")
    suspend fun setUserProfile(
        @Body profile: ProfileRequest
    ): Response<ProfileResponse>
}