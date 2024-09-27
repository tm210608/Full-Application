package com.mito.login.data

import com.mito.database.data.dao.UserDao
import com.mito.login.data.mapper.toEntity
import com.mito.login.domain.UserDataSource
import com.mito.network.BuildConfig
import com.mito.network.dummy_login.domain.User
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {
    override suspend fun registerUser(user: User) {
        userDao.insert(user.toEntity())
    }

    override suspend fun checkCredentialsDataBase(username: String, password: String): Int? {
        return userDao.getUserId(username, password) ?: (-1).takeIf { BuildConfig.DEBUG }
    }
}