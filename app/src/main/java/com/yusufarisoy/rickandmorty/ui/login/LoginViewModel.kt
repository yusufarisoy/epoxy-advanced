package com.yusufarisoy.rickandmorty.ui.login

import android.util.Log
import com.yusufarisoy.common.StateError
import com.yusufarisoy.core.utils.StatefulViewModel
import com.yusufarisoy.core.utils.UiState
import com.yusufarisoy.rickandmorty.ui.login.LoginViewModel.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : StatefulViewModel<LoginState>(LoginState()) {

    fun setEmailText(emailText: String?) = setState {
        copy(emailText = emailText)
    }

    fun setPasswordText(password: String) = setState {
        copy(passwordText = password)
    }

    fun loginControl() = with (currentUiState) {
        if (emailText == null || passwordText == null) {
            setError(StateError(exception = null, "Invalid credentials"))
        } else {
            login()
        }
    }

    private fun login() {
        Log.v("LoginViewModel", "Email: ${currentUiState.emailText?: "NULL"}")
        Log.v("LoginViewModel", "Password: ${currentUiState.passwordText?: "NULL"}")
    }

    data class LoginState(
        val emailText: String? = null,
        val passwordText: String? = null
    ) : UiState
}