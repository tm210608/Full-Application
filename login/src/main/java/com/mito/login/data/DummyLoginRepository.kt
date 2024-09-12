package com.mito.login.data

import com.mito.database.data.dao.UserDao
import com.mito.network.BuildConfig
import com.mito.network.dummy_login.data.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DummyLoginRepository {
    suspend fun login(username: String, password: String): Pair<Result<LoginResponse>, Int?>
}

class DummyLoginRepositoryImpl @Inject constructor(
    private val dataSource: DummyLoginDataSource,
    private val userDao: UserDao
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
        return userDao.getUserId(username, password) ?: (-1).takeIf { BuildConfig.DEBUG }
    }
}