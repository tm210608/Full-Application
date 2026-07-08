package com.mito.login.domain

import com.mito.network.auth.domain.User
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserDataRepository) {
    suspend operator fun invoke(user: User) = repository.registerUser(user)
}