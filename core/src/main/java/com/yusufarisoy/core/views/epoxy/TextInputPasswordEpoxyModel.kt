package com.yusufarisoy.core.views.epoxy

import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import androidx.core.widget.addTextChangedListener
import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.yusufarisoy.core.R
import com.yusufarisoy.core.databinding.EpoxyModelTextInputPasswordBinding
import com.yusufarisoy.core.utils.ViewBindingEpoxyModel
import com.yusufarisoy.core.utils.setInputText

@EpoxyModelClass
abstract class TextInputPasswordEpoxyModel : ViewBindingEpoxyModel<EpoxyModelTextInputPasswordBinding>() {

    @StringRes
    @EpoxyAttribute
    var hint: Int = R.string.password

    @EpoxyAttribute var text: String? = null

    @EpoxyAttribute
    lateinit var onTextChanged: (text: String) -> Unit

    //Variables
    private var isPasswordVisible = false

    private lateinit var textWatcher: TextWatcher

    override fun EpoxyModelTextInputPasswordBinding.bind() {
        editTextInput.setHint(hint)
        setInputText(editTextInput, text)
        textWatcher = editTextInput.addTextChangedListener {
            onTextChanged(it.toString())
        }

        buttonChangeInputType.setOnClickListener {
            editTextInput.transformationMethod = if (isPasswordVisible) null
            else
                PasswordTransformationMethod.getInstance()
            editTextInput.setSelection(editTextInput.length())
            isPasswordVisible = !isPasswordVisible
        }
    }

    override fun EpoxyModelTextInputPasswordBinding.unbind() {
        editTextInput.removeTextChangedListener(textWatcher)
        buttonChangeInputType.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_text_input_password
}