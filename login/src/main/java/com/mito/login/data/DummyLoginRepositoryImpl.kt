package com.mito.login.data

import com.mito.login.domain.DummyLoginDataSource
import com.mito.login.domain.DummyLoginRepository
import com.mito.login.domain.UserDataSource
import com.mito.network.BuildConfig
import com.mito.network.dummy_login.data.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DummyLoginRepositoryImpl @Inject constructor(
    private val dataSource: DummyLoginDataSource,
    private val userDataSource: UserDataSource
) : DummyLoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Pair<Result<LoginResponse>, Int?> {
        return withContext(Dispatchers.IO) {
            val userId: Int? = checkCredentialsDataBase(username, password)
            val usernameMock = BuildConfig.USERNAME
            val passwordMock = BuildConfig.PASSWORD
            Pair(dataSource.login(
                (userId?.takeIf { it != -1 }?.let { usernameMock } ?: username),
                (userId?.takeIf { it != -1 }?.let { passwordMock} ?: password)
            ), userId)
        }
    }

    private suspend fun checkCredentialsDataBase(username: String, password: String): Int? {
        return userDataSource.checkCredentialsDataBase(username, password)
    }
}