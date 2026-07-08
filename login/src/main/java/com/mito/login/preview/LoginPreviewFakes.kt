package com.mito.login.preview

import com.mito.database.data.dao.UserDao
import com.mito.database.data.entity.UserEntity
import com.mito.network.auth.data.LoginService
import com.mito.network.auth.data.request.LoginRequest
import com.mito.network.auth.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class FakeLoginService : LoginService {

    private val fakeMessage = "fake_token"
    override suspend fun login(
        loginRequest: LoginRequest,
        contentType: String,
    ): Response<LoginResponse> {
        return Response.success(LoginResponse(fakeMessage, "ok"))
    }
}

object FakeUserDao : UserDao {
    override fun getAll(): Flow<List<UserEntity>> = flowOf(emptyList())

    override suspend fun getUserId(email: String, password: String): Int? = null

    override suspend fun insert(user: UserEntity) = Unit

    override suspend fun update(user: UserEntity) = Unit

    override suspend fun delete(user: UserEntity) = Unit
}
