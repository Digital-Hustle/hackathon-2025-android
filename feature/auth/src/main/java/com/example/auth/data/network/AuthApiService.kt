package com.example.auth.data.network

import com.example.auth.domain.models.GoogleAuthRequest
import com.example.auth.domain.models.LoginRequest
import com.example.auth.domain.models.LoginResponse
import com.example.auth.domain.models.RegistrationRequest
import com.example.auth.domain.models.RegistrationResponse
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