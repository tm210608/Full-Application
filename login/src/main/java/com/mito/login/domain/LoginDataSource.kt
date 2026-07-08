package com.mito.login.domain

import com.mito.network.auth.data.response.LoginResponse


interface LoginDataSource {
    suspend fun login(username: String, password: String): Result<LoginResponse>
}