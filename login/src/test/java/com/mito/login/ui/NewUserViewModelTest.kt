package com.mito.login.ui

import com.mito.login.domain.RegisterUserUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewUserViewModelTest {

    private val registerUserUseCase: RegisterUserUseCase = mockk(relaxed = true)
    private lateinit var viewModel: NewUserViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewUserViewModel(registerUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `email validation returns true for valid email`() {
        val result = viewModel.isValidEmail("user@example.com")
        assertTrue(result)
    }

    @Test
    fun `email validation returns false for invalid email`() {
        val result = viewModel.isValidEmail("invalid-email")
        assertFalse(result)
    }

    @Test
    fun `password validation returns true for valid password`() {
        val result = viewModel.isValidPassword("Password1@")
        assertTrue(result)
    }

    @Test
    fun `password validation returns false for short password`() {
        val result = viewModel.isValidPassword("Sh@rt1")
        assertFalse(result)
    }

    @Test
    fun `password validation returns false for password without uppercase`() {
        val result = viewModel.isValidPassword("password1@")
        assertFalse(result)
    }

    @Test
    fun `confirm password validation`() {
        assertTrue(viewModel.isValidConfirmPassword("pass123", "pass123"))
        assertFalse(viewModel.isValidConfirmPassword("pass123", "pass456"))
    }

    @Test
    fun `onUserNamedChanged updates username`() {
        viewModel.onUserNamedChanged("John")
        assertEquals("John", viewModel.status.value.username)
    }

    @Test
    fun `onEmailChanged updates email`() {
        viewModel.onEmailChanged("john@example.com")
        assertEquals("john@example.com", viewModel.status.value.email)
    }

    @Test
    fun `isButtonEnabled returns false when email is invalid`() {
        viewModel.onUserNamedChanged("John")
        viewModel.onEmailChanged("invalid")
        viewModel.onPasswordChanged("ValidPass1@")
        viewModel.onConfirmPasswordChanged("ValidPass1@")
        assertFalse(viewModel.isButtonEnabled(viewModel.status.value))
    }

    @Test
    fun `confirmRegistration calls registerUserUseCase`() = runTest {
        viewModel.onUserNamedChanged("John")
        viewModel.onEmailChanged("john@example.com")
        viewModel.onPasswordChanged("ValidPass1@")
        viewModel.onConfirmPasswordChanged("ValidPass1@")
        viewModel.onGenderChanged("Male")
        viewModel.onAddressChanged("123 Street")
        viewModel.onCityChanged("City")
        viewModel.onStateChanged("Madrid")
        viewModel.onNumberPhoneChanged("+34123456789")
        viewModel.onBirthDateChanged("01/01/2000")

        viewModel.confirmRegistration()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { registerUserUseCase(any()) }
        assertTrue(viewModel.status.value.isRegistered)
    }
}
