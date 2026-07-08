package com.mito.login.domain

import com.mito.network.auth.domain.User

interface UserDataRepository {
    suspend fun registerUser(user: User)
}