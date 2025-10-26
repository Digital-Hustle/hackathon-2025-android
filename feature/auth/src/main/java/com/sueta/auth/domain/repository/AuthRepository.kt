package com.sueta.auth.domain.repository

import com.sueta.auth.domain.models.LoginRequest
import com.sueta.auth.domain.models.LoginResponse
import com.sueta.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import com.sueta.auth.domain.models.RegistrationRequest
import com.sueta.auth.domain.models.RegistrationResponse

interface AuthRepository {

    suspend fun setId(id: String)
    suspend fun setUserName(name: String)

    fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>>

    fun loginWithGoogle():Flow<ApiResponse<LoginResponse>>

    fun registration(registrationRequest: RegistrationRequest):Flow<ApiResponse<RegistrationResponse>>
}