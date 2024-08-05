package com.mito.common.usecase

sealed interface Result {
    data class Error(val message: String): Result
    data class Success<T>(val value: T? = null) : Result
}