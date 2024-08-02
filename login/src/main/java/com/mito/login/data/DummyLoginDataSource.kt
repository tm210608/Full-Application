package com.mito.login.data

import com.mito.network.dummy_login.data.LoginService
import com.mito.network.dummy_login.data.request.LoginRequest
import com.mito.network.dummy_login.data.response.LoginResponse
import javax.inject.Inject


interface DummyLoginDataSource {
    suspend fun login(username: String, password: String): Result<LoginResponse>
}

class DummyLoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): DummyLoginDataSource {
    override suspend fun login(username: String, password: String): Result<LoginResponse> {
        try {
            val response = loginService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    return when (it.status) {
                        SUCCESS -> Result.success(it)
                        ERROR -> Result.failure(Exception(it.message))
                        else -> Result.failure(Exception("Unknown status"))
                    }
                } ?: run {
                    return Result.failure(Exception("Response body is null"))
                }
            } else {
                return Result.failure(Exception("Response is not successful"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    companion object{
        private const val ERROR = "error"
        private const val SUCCESS = "ok"
    }
}