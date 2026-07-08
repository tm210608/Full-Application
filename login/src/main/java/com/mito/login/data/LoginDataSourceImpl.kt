package com.mito.login.data

import com.mito.login.domain.LoginDataSource
import com.mito.network.auth.data.LoginService
import com.mito.network.auth.data.request.LoginRequest
import com.mito.network.auth.data.response.LoginResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): LoginDataSource {
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