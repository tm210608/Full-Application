package com.mito.common.usecase

sealed interface Result<out T> {
    data class Error(val message: String): Result<Nothing>
    data class Success<T>(val value: T? = null) : Result<T>
}