package com.yusufarisoy.rickandmorty.ui.register

import com.yusufarisoy.common.StateError
import com.yusufarisoy.rickandmorty.utils.CoroutineTestRule
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @InjectMockKs
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel()
    }

    @Test
    fun `When setEmailText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val email = "yusuf@gmail.com"
        registerViewModel.setEmailText(email)

        Assertions.assertThat(
            registerViewModel.currentUiState.emailText
        ).isEqualTo(email)
    }

    @Test
    fun `When setNameText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val name = "Yusuf"
        registerViewModel.setNameText(name)

        Assertions.assertThat(
            registerViewModel.currentUiState.nameText
        ).isEqualTo(name)
    }

    @Test
    fun `When setSurnameText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val surname = "ARISOY"
        registerViewModel.setSurnameText(surname)

        Assertions.assertThat(
            registerViewModel.currentUiState.surnameText
        ).isEqualTo(surname)
    }

    @Test
    fun `When setPasswordText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val password = "123Asd"
        registerViewModel.setPasswordText(password)

        Assertions.assertThat(
            registerViewModel.currentUiState.passwordText
        ).isEqualTo(password)
    }

    @Test
    fun `When setPasswordRepeatText() called state's getting set correctly`() = coroutineTestRule.runBlockingTest {

        val passwordRepeat = "123Asd"
        registerViewModel.setPasswordRepeatText(passwordRepeat)

        Assertions.assertThat(
            registerViewModel.currentUiState.passwordRepeatText
        ).isEqualTo(passwordRepeat)
    }

    fun `When any of the inputs are null error will shown`() = coroutineTestRule.runBlockingTest {

        val email = "yusuf@gmail.com"
        val error = StateError(exception = null, "Invalid credentials")

        registerViewModel.setEmailText(email)

        registerViewModel.registerControl()

        Assertions.assertThat(
            registerViewModel.stateFlow.value.error
        ).isEqualTo(error)
    }
}