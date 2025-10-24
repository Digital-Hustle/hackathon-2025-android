package com.example.authtest.domain.repository

import com.example.auth.domain.models.LoginRequest
import com.example.auth.domain.models.LoginResponse
import com.example.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import com.example.auth.domain.models.RegistrationRequest
import com.example.auth.domain.models.RegistrationResponse

interface AuthRepository {

    suspend fun setId(id: Int)
    suspend fun setUserName(name: String)

    fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>>

    fun loginWithGoogle():Flow<ApiResponse<LoginResponse>>

    fun registration(registrationRфequest: RegistrationRequest):Flow<ApiResponse<RegistrationResponse>>


}