package com.sueta.authtest.data.repository

import com.sueta.auth.data.network.AuthApiService
import com.sueta.auth.domain.models.GoogleAuthRequest
import com.sueta.auth.data.auth.GoogleManager
import com.sueta.auth.domain.models.LoginRequest
import com.sueta.auth.domain.models.RegistrationRequest
import com.sueta.auth.domain.repository.AuthRepository
import com.sueta.core.domain.UserStorage
import com.sueta.network.apiRequestFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val storage:UserStorage,
    private val googleManager: GoogleManager
): AuthRepository {
    override fun login(loginRequest: LoginRequest) = apiRequestFlow {

        authApiService.login(loginRequest)
    }

    override suspend fun setId(id: String) {
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