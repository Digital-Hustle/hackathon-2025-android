package com.sueta.network.Interface

import com.sueta.network.models.Refresh
import com.sueta.network.models.RefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiService {


    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(
        @Body refresh: Refresh
    ): Response<RefreshResponse>


}