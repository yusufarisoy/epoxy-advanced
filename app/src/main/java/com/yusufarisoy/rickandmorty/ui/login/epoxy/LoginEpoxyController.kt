package com.yusufarisoy.rickandmorty.ui.login.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.yusufarisoy.core.utils.SimplifiedTextWatcher
import com.yusufarisoy.core.views.epoxy.textInput
import com.yusufarisoy.core.views.epoxy.textInputPassword
import com.yusufarisoy.core.views.epoxy.title
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.ui.login.LoginViewModel.LoginState

class LoginEpoxyController(
    private val _loginTextWatchers: LoginTextWatchers,
    private val _loginCallBacks: LoginCallbacks
) : TypedEpoxyController<LoginState>() {

    interface LoginTextWatchers {
        val emailTextWatcher: SimplifiedTextWatcher
        fun onPasswordChanged(password: String)
    }

    interface LoginCallbacks {
        fun loginOnClick()
        fun registerOnClick()
    }

    override fun buildModels(data: LoginState) {
        buildTitle()
        buildEmailInput(data.emailText)
        buildPasswordInput(data.passwordText)
        buildButtons()
    }

    private fun buildTitle() {
        title {
            id("id_page_title")
            text("Login")
        }
    }

    private fun buildEmailInput(emailText: String?) {
        textInput {
            id("id_text_input_email")
            hint(R.string.email)
            text(emailText)
            textWatcher(this@LoginEpoxyController._loginTextWatchers.emailTextWatcher)
        }
    }

    private fun buildPasswordInput(password: String?) {
        textInputPassword {
            id("id_text_input_password")
            text(password)
            onTextChanged(this@LoginEpoxyController._loginTextWatchers::onPasswordChanged)
        }
    }

    private fun buildButtons() {
        loginButtons {
            id("id_login_buttons")
            loginOnClick {
                this@LoginEpoxyController._loginCallBacks.loginOnClick()
            }
            registerOnClick {
                this@LoginEpoxyController._loginCallBacks.registerOnClick()
            }
        }
    }
}