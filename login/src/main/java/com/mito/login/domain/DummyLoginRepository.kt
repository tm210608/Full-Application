package com.mito.login.domain

import com.mito.network.dummy_login.data.response.LoginResponse

interface DummyLoginRepository {
    suspend fun login(username: String, password: String): Pair<Result<LoginResponse>, Int?>
}