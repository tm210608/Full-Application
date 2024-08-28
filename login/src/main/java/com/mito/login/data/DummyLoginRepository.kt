package com.mito.login.data

import com.mito.network.dummy_login.data.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DummyLoginRepository {
    suspend fun login(username: String, password: String): Result<Pair<LoginResponse, Int>>
}

class DummyLoginRepositoryImpl @Inject constructor(
    private val dataSource: DummyLoginDataSource
) : DummyLoginRepository {
    override suspend fun login(username: String, password: String): Result<Pair<LoginResponse, Int>> {
        return withContext(Dispatchers.IO) {
            dataSource.login(username, password)
        }
    }
}