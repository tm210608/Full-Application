package com.mito.login.domain

import com.mito.login.ui.LoginUIModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseTest {

    private val repository: LoginRepository = mockk()
    private val useCase = LoginUseCase(repository)

    @Test
    fun `invoke returns success when login succeeds`() = runTest {
        val input = Input("user@example.com", "password")
        coEvery { repository.login(input.email, input.password) } returns Pair(
            Result.success(com.mito.network.auth.data.response.LoginResponse("Login OK", "ok")),
            1
        )

        val result = useCase(input).first()

        assertTrue(result is com.mito.common.usecase.Result.Success)
        val loginModel = (result as com.mito.common.usecase.Result.Success<LoginUIModel>).value
        assertEquals("Login OK", loginModel.message)
    }

    @Test
    fun `invoke returns error when login fails`() = runTest {
        val input = Input("wrong@example.com", "wrong")
        coEvery { repository.login(input.email, input.password) } returns Pair(
            Result.failure(Exception("Invalid credentials")),
            null
        )

        val result = useCase(input).first()

        assertTrue(result is com.mito.common.usecase.Result.Error)
    }
}
