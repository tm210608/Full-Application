package com.mito.login.data

import com.mito.login.domain.UserDataRepository
import com.mito.login.domain.UserDataSource
import com.mito.network.dummy_login.domain.User
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) :
    UserDataRepository {
    override suspend fun registerUser(user: User) {
        dataSource.registerUser(user)
    }
}