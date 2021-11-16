package com.yusufarisoy.core.views.epoxy

import android.text.TextWatcher
import androidx.annotation.StringRes
import com.airbnb.epoxy.*
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.yusufarisoy.core.R
import com.yusufarisoy.core.databinding.EpoxyModelTextInputBinding
import com.yusufarisoy.core.utils.*

@EpoxyModelClass
abstract class TextInputEpoxyModel : ViewBindingEpoxyModel<EpoxyModelTextInputBinding>() {

    @StringRes
    @EpoxyAttribute
    var hint: Int? = null

    @EpoxyAttribute
    var text: String? = null

    @EpoxyAttribute(DoNotHash)
    lateinit var textWatcher: TextWatcher

    override fun EpoxyModelTextInputBinding.bind() {
        editTextInput.setNullableHint(hint)
        editTextInput.setInputText(text)
        editTextInput.addTextChangedListener(textWatcher)
    }

    override fun EpoxyModelTextInputBinding.unbind() {
        editTextInput.removeTextChangedListener(textWatcher)
    }

    override fun getDefaultLayout(): Int = R.layout.epoxy_model_text_input
}