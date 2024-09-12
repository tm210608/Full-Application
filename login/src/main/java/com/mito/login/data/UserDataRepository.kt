package com.mito.login.data

import com.mito.network.dummy_login.domain.User
import javax.inject.Inject

interface UserDataRepository {
    suspend fun registerUser(user: User)
}

class UserDataRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) :
    UserDataRepository {
    override suspend fun registerUser(user: User) {
        dataSource.registerUser(user)
    }
}