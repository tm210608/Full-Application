package com.mito.network.auth.data

import com.mito.network.auth.data.request.LoginRequest
import com.mito.network.auth.data.response.LoginResponse
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

interface LoginService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("Content-Type") contentType: String = "application/json"
        ): Response<LoginResponse>
}