package com.yusufarisoy.core.views.epoxy

import android.text.TextWatcher
import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.yusufarisoy.core.R
import com.yusufarisoy.core.databinding.EpoxyModelTextInputPasswordBinding
import com.yusufarisoy.core.utils.SimpleTextWatcher
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel

@EpoxyModelClass
abstract class TextInputPasswordEpoxyModel : ViewBindingEpoxyModel<EpoxyModelTextInputPasswordBinding>() {

    @StringRes
    @EpoxyAttribute
    var hint: Int = R.string.password

    @EpoxyAttribute
    var text: String? = null

    @EpoxyAttribute(DoNotHash)
    lateinit var textWatcher: TextWatcher

    private var isPasswordVisible = false

    override fun EpoxyModelTextInputPasswordBinding.bind() {
        editTextInput.setHint(hint)
        editTextInput.setInputText(text)
        editTextInput.addTextChangedListener(textWatcher)
        buttonChangeInputType.setOnClickListener {
            editTextInput.changeInputVisibility(isPasswordVisible)
            isPasswordVisible = isPasswordVisible.not()
        }
    }

    override fun EpoxyModelTextInputPasswordBinding.unbind() {
        editTextInput.removeTextChangedListener(textWatcher)
        buttonChangeInputType.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_text_input_password
}