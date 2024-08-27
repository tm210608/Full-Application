package com.mito.login.domain

import com.mito.common.usecase.Result
import com.mito.login.data.DummyLoginRepository
import com.mito.login.ui.LoginUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DummyLoginUseCase @Inject constructor(private val repository: DummyLoginRepository){

    suspend operator fun invoke(input: Input): Flow<Result<LoginUIModel>>  = flow {
        repository.login(input.email, input.password)
            .onSuccess{
                emit(Result.Success(LoginUIModel(it.first.message, it.second)))
            }
            .onFailure{ emit(Result.Error(it.message ?: "")) }
    }
}

data class Input(val email : String, val password : String)
