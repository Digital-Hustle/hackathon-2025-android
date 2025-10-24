package com.example.authtest.data.repository

import com.example.auth.data.network.AuthApiService
import com.example.auth.domain.models.GoogleAuthRequest
import com.example.auth.data.auth.GoogleManager
import com.example.auth.domain.models.LoginRequest
import com.example.auth.domain.models.RegistrationRequest
import com.example.authtest.domain.repository.AuthRepository
import com.example.core.domain.UserStorage
import com.example.network.apiRequestFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val storage:UserStorage,
    private val googleManager: GoogleManager
): AuthRepository {
    override fun login(loginRequest: LoginRequest) = apiRequestFlow {

        authApiService.login(loginRequest)
    }




    override suspend fun setId(id: Int) {
        storage.setId(id)
    }

    override suspend fun setUserName(name: String) {
        storage.setUsername(name)
    }
    override fun loginWithGoogle() = apiRequestFlow {
        val l = googleManager.signIn()


        authApiService.googleOAuth(GoogleAuthRequest(l))
    }

    override fun registration(registrationRequest: RegistrationRequest) = apiRequestFlow{
        authApiService.registration(registrationRequest)
    }


}