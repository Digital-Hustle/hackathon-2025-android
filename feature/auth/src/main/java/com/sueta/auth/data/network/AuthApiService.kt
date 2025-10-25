package com.sueta.auth.data.network

import com.sueta.auth.domain.models.GoogleAuthRequest
import com.sueta.auth.domain.models.LoginRequest
import com.sueta.auth.domain.models.LoginResponse
import com.sueta.auth.domain.models.RegistrationRequest
import com.sueta.auth.domain.models.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>


    @POST("/api/v1/auth/register")
    suspend fun registration(
        @Body registrationRequest: RegistrationRequest
    ):Response<RegistrationResponse>

    @POST("/api/v1/auth/google")
    suspend fun googleOAuth(
        @Body googleAuthRequest: GoogleAuthRequest
    ): Response<LoginResponse>

}