package com.mito.login.data

import com.mito.database.data.dao.UserDao
import com.mito.login.data.mapper.toEntity
import com.mito.network.dummy_login.domain.User
import javax.inject.Inject

interface UserDataSource {
    suspend fun registerUser(user: User)
}

class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {
    override suspend fun registerUser(user: User) {
        userDao.insert(user.toEntity())
    }
}