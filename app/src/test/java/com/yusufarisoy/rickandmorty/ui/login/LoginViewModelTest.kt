package com.yusufarisoy.rickandmorty.ui.login

import com.yusufarisoy.common.StateError
import com.yusufarisoy.rickandmorty.utils.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @InjectMockKs
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel()
    }

    @Test
    fun `When setEmailText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val email = "yusuf@gmail.com"
        loginViewModel.setEmailText(email)

        assertThat(
            loginViewModel.currentUiState.emailText
        ).isEqualTo(email)
    }

    @Test
    fun `When setPasswordText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val password = "123Asd"
        loginViewModel.setPasswordText(password)

        assertThat(
            loginViewModel.currentUiState.passwordText
        ).isEqualTo(password)
    }

    fun `When any of the inputs are null error will shown`() = coroutineTestRule.runBlockingTest {

        val email = "yusuf@gmail.com"
        val error = StateError(exception = null, "Invalid credentials")

        loginViewModel.setEmailText(email)

        loginViewModel.loginControl()

        assertThat(
            loginViewModel.stateFlow.value.error
        ).isEqualTo(error)
    }
}