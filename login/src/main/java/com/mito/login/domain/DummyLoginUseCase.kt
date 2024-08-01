package com.mito.login.domain

interface DummyLoginUseCase {
    suspend fun login(username: String, password: String): Result<LoginResponse>
}