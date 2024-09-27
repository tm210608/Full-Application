package com.mito.login.domain

import com.mito.network.dummy_login.domain.User

interface UserDataRepository {
    suspend fun registerUser(user: User)
}