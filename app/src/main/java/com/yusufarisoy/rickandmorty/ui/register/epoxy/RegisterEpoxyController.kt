package com.yusufarisoy.rickandmorty.ui.register.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.yusufarisoy.core.utils.SimpleTextWatcher
import com.yusufarisoy.core.views.epoxy.textInput
import com.yusufarisoy.core.views.epoxy.textInputPassword
import com.yusufarisoy.core.views.epoxy.title
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.ui.register.RegisterViewModel.RegisterState

class RegisterEpoxyController(
    private val _registerTextWatchers: RegisterTextWatchers,
    private val _registerOnClick: () -> Unit
) : TypedEpoxyController<RegisterState>() {

    interface RegisterTextWatchers {
        val emailTextWatcher: SimpleTextWatcher
        val nameTextWatcher: SimpleTextWatcher
        val surnameTextWatcher: SimpleTextWatcher
        fun onPasswordChanged(password: String?)
        fun onPasswordRepeatChanged(passwordRepeat: String?)
    }

    override fun buildModels(data: RegisterState) {
        buildTitle()
        buildEmailInput(data.emailText)
        buildNameInput(data.nameText)
        buildSurnameInput(data.surnameText)
        buildPasswordInput(data.passwordText)
        buildPasswordRepeatInput(data.passwordRepeatText)
        buildRegisterButton()
    }

    private fun buildTitle() {
        title {
            id("id_register_title")
            text("Register")
        }
    }

    private fun buildEmailInput(emailText: String?) {
        textInput {
            id("id_text_input_email")
            hint(R.string.email)
            text(emailText)
            textWatcher(this@RegisterEpoxyController._registerTextWatchers.emailTextWatcher)
        }
    }

    private fun buildNameInput(nameText: String?) {
        textInput {
            id("id_text_input_name")
            hint(R.string.name)
            text(nameText)
            textWatcher(this@RegisterEpoxyController._registerTextWatchers.nameTextWatcher)
        }
    }

    private fun buildSurnameInput(surnameText: String?) {
        textInput {
            id("id_text_input_surname")
            hint(R.string.surname)
            text(surnameText)
            textWatcher(this@RegisterEpoxyController._registerTextWatchers.surnameTextWatcher)
        }
    }

    private fun buildPasswordInput(passwordText: String?) {
        textInputPassword {
            id("id_text_input_password")
            text(passwordText)
            onTextChanged(this@RegisterEpoxyController._registerTextWatchers::onPasswordChanged)
        }
    }

    private fun buildPasswordRepeatInput(passwordRepeatText: String?) {
        textInputPassword {
            id("id_text_input_password_repeat")
            hint(R.string.password_repeat)
            text(passwordRepeatText)
            onTextChanged(this@RegisterEpoxyController._registerTextWatchers::onPasswordRepeatChanged)
        }
    }

    private fun buildRegisterButton() {
        registerButton {
            id("id_register_button")
            registerOnClick(this@RegisterEpoxyController._registerOnClick)
        }
    }
}