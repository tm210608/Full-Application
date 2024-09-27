package com.mito.login.domain

import com.mito.network.dummy_login.data.response.LoginResponse


interface DummyLoginDataSource {
    suspend fun login(username: String, password: String): Result<LoginResponse>
}