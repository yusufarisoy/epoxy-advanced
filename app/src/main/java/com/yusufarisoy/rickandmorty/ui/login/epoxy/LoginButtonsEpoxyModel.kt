package com.yusufarisoy.rickandmorty.ui.login.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.databinding.EpoxyModelLoginButtonsBinding

@EpoxyModelClass
abstract class LoginButtonsEpoxyModel : ViewBindingEpoxyModel<EpoxyModelLoginButtonsBinding>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var loginOnClick: () -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var registerOnClick: () -> Unit

    override fun EpoxyModelLoginButtonsBinding.bind() {
        buttonLogin.setOnClickListener {
            loginOnClick()
        }
        buttonRegister.setOnClickListener {
            registerOnClick()
        }
    }

    override fun EpoxyModelLoginButtonsBinding.unbind() {
        buttonLogin.setOnClickListener(null)
        buttonRegister.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_login_buttons
}