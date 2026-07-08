package com.mito.login.domain

import com.mito.network.auth.domain.User

interface UserDataSource {
    suspend fun registerUser(user: User)
    suspend fun checkCredentialsDataBase(username: String, password: String): Int?
}