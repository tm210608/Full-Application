package com.mito.login.data

import com.mito.login.domain.LoginDataSource
import com.mito.login.domain.LoginRepository
import com.mito.login.domain.UserDataSource
import com.mito.network.BuildConfig
import com.mito.network.auth.data.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val userDataSource: UserDataSource
) : LoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Pair<Result<LoginResponse>, Int?> {
        return withContext(Dispatchers.IO) {
            val userId: Int? = checkCredentialsDataBase(username, password)
            val usernameMock = BuildConfig.USERNAME
            val passwordMock = BuildConfig.PASSWORD
            Pair(loginDataSource.login(
                (userId?.takeIf { it != -1 }?.let { usernameMock } ?: username),
                (userId?.takeIf { it != -1 }?.let { passwordMock} ?: password)
            ), userId)
        }
    }

    private suspend fun checkCredentialsDataBase(username: String, password: String): Int? {
        return userDataSource.checkCredentialsDataBase(username, password)
    }
}