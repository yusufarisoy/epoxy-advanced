package com.yusufarisoy.rickandmorty.ui.register

import android.util.Log
import com.yusufarisoy.common.StateError
import com.yusufarisoy.rickandmorty.ui.register.RegisterViewModel.RegisterState
import com.yusufarisoy.core.utils.StatefulViewModel
import com.yusufarisoy.core.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : StatefulViewModel<RegisterState>(RegisterState()) {

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: Flow<Boolean> = _navigateBack

    fun setEmailText(emailText: String?) = setState {
        copy(emailText = emailText)
    }

    fun setNameText(nameText: String?) = setState {
        copy(nameText = nameText)
    }

    fun setSurnameText(surnameText: String?) = setState {
        copy(surnameText = surnameText)
    }

    fun setPasswordText(passwordText: String?) = setState {
        copy(passwordText = passwordText)
    }

    fun setPasswordRepeatText(passwordRepeatText: String?) = setState {
        copy(passwordRepeatText = passwordRepeatText)
    }

    fun registerControl() = with (currentUiState) {
        if (emailText == null || nameText == null || surnameText == null || passwordText == null || passwordRepeatText == null) {
            setError(StateError(exception = null, "Invalid credentials"))
        } else {
            register()
        }
    }

    private fun register() {
        Log.v("RegisterViewModel", "Email: ${currentUiState.emailText?: "NULL"}")
        Log.v("RegisterViewModel", "Name: ${currentUiState.nameText?: "NULL"}")
        Log.v("RegisterViewModel", "Surname: ${currentUiState.surnameText?: "NULL"}")
        Log.v("RegisterViewModel", "Password: ${currentUiState.passwordText?: "NULL"}")
        Log.v("RegisterViewModel", "PasswordRepeat: ${currentUiState.passwordRepeatText?: "NULL"}")
        _navigateBack.value = true
    }

    data class RegisterState(
        val emailText: String? = null,
        val nameText: String? = null,
        val surnameText: String? = null,
        val passwordText: String? = null,
        val passwordRepeatText: String? = null,
    ) : UiState
}