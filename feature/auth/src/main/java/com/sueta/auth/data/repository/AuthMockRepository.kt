package com.sueta.auth.data.repository

import com.sueta.auth.data.auth.GoogleManager
import com.sueta.auth.domain.models.LoginRequest
import com.sueta.auth.domain.models.LoginResponse
import com.sueta.auth.domain.models.RegistrationRequest
import com.sueta.auth.domain.models.RegistrationResponse
import com.sueta.auth.domain.repository.AuthRepository
import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.di.Mockup
import com.sueta.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthMockRepository @Inject constructor(
    private val storage: UserStorage,
    private val googleManager: GoogleManager
) : AuthRepository, Mockup {
    override suspend fun setId(id: String) {
        storage.setId(id)
    }

    override suspend fun setUserName(name: String) {
        storage.setUsername(name)
    }

    override fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>> =
        flow {
            emit(
                ApiResponse.Success(
                    LoginResponse(
                        token = "mock-jwt-token-12345",
                        id = "34",
                        username = loginRequest.username,
                        refreshToken = "mock-jwt-refresh-12345"
                    )
                )
            )
        }

    override fun loginWithGoogle(): Flow<ApiResponse<LoginResponse>> {
        googleManager.signIn()

        return flow {

            emit(
                ApiResponse.Success(
                    LoginResponse(
                        token = "mock-jwt-token-12345",
                        id = "34",
                        username = "Test User",
                        refreshToken = "mock-jwt-refresh-12345"
                    )
                )
            )


        }
    }

    override fun registration(registrationRequest: RegistrationRequest): Flow<ApiResponse<RegistrationResponse>> =
        flow {
            emit(
                ApiResponse.Success(
                    RegistrationResponse(
                        username = registrationRequest.username,
                    )
                )
            )
        }
}