package com.mito.login.domain

import com.mito.network.auth.data.response.LoginResponse

interface LoginRepository {
    suspend fun login(username: String, password: String): Pair<Result<LoginResponse>, Int?>
}