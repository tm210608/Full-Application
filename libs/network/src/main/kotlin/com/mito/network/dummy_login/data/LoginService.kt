package com.mito.network.dummy_login.data

import com.mito.network.dummy_login.data.response.LoginResponse
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response

interface LoginService {
    @POST("login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}