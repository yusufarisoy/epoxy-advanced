package com.yusufarisoy.rickandmorty.ui.register.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.databinding.EpoxyModelRegisterButtonBinding

@EpoxyModelClass
abstract class RegisterButtonEpoxyModel : ViewBindingEpoxyModel<EpoxyModelRegisterButtonBinding>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var registerOnClick: () -> Unit

    override fun EpoxyModelRegisterButtonBinding.bind() {
        buttonRegister.setOnClickListener {
            registerOnClick()
        }
    }

    override fun EpoxyModelRegisterButtonBinding.unbind() {
        buttonRegister.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_register_button
}