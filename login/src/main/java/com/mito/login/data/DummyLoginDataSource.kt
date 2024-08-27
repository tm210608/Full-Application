package com.mito.login.data

import com.mito.database.data.dao.UserDao
import com.mito.network.BuildConfig
import com.mito.network.dummy_login.data.LoginService
import com.mito.network.dummy_login.data.request.LoginRequest
import com.mito.network.dummy_login.data.response.LoginResponse
import javax.inject.Inject


interface DummyLoginDataSource {
    suspend fun login(username: String, password: String): Result<Pair<LoginResponse, Int>>
}

class DummyLoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
    private val userDao: UserDao
): DummyLoginDataSource {
    override suspend fun login(username: String, password: String): Result<Pair<LoginResponse, Int>> {
        val userId = checkCredentialsDataBase(username, password)
        val usernameMock = BuildConfig.USERNAME
        val passwordMock = BuildConfig.PASSWORD
        try {
            val response =
                loginService.login(LoginRequest(usernameMock.takeIf { userId != null } ?: username,
                    passwordMock.takeIf { userId != null } ?: password))
            if (response.isSuccessful) {
                response.body()?.let {
                    return when (it.status) {
                        SUCCESS -> Result.success(Pair(it, userId!!))
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

    private suspend fun checkCredentialsDataBase(username:String, password: String): Int? {
        return userDao.getUserId(username, password)
    }

    companion object{
        private const val ERROR = "error"
        private const val SUCCESS = "ok"
    }
}