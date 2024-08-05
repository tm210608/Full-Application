package com.mito.login.domain

import com.mito.common.usecase.Result
import com.mito.login.data.DummyLoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DummyLoginUseCase @Inject constructor(private val repository: DummyLoginRepository){

    suspend operator fun invoke(input: Input): Flow<Result>  = flow {
        repository.login(input.email, input.password)
            .onSuccess {
                emit(Result.Success(it.message))
            }
            .onFailure {
                emit(Result.Error(it.message ?: "Error"))
            }
    }
}

data class Input(val email : String, val password : String)
