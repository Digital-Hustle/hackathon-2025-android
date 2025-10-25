package com.sueta.network

import com.sueta.core.mLog
import com.sueta.network.storage.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        val request = chain.request().newBuilder()
        mLog("tetet",chain.request().toString())

        if (chain.request().tag(Object::class.java) == "no-auth") {
            mLog("tetet","noaut")
            return chain.proceed(request.build())
        }

        if (token != null) {
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}