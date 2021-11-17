package com.yusufarisoy.core.views.custom

import android.content.Context
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText

class ExtendedTextInputEditText(
    context: Context,
    attrs: AttributeSet
) : TextInputEditText(context, attrs) {

    private val textChangeListeners = mutableListOf<TextWatcher?>()

    fun setNullableHint(@StringRes hint: Int?) {
        hint?.let {
            setHint(it)
        }
    }

    fun setInputText(newText: CharSequence?) {
        if (text.isChanged(newText)) {
            setText(newText)
            setSelection(this.length())
        }
    }

    fun changeInputVisibility(isTextVisible: Boolean) {
        transformationMethod = if (isTextVisible.not()) {
            null
        } else {
            PasswordTransformationMethod.getInstance()
        }
        setSelection(this.length())
    }

    override fun addTextChangedListener(watcher: TextWatcher?) {
        if (textChangeListeners.contains(watcher).not()) {
            textChangeListeners.add(watcher)
            super.addTextChangedListener(watcher)
        }
    }

    override fun removeTextChangedListener(watcher: TextWatcher?) {
        textChangeListeners.remove(watcher)
        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        if (textChangeListeners.isNotEmpty()) {
            textChangeListeners.forEach(::removeTextChangedListener)
            textChangeListeners.clear()
        }
    }

    private fun CharSequence?.isChanged(newText: CharSequence?): Boolean {
        if (newText == this) {
            return false
        }

        if ((newText == null || this == null) || (newText.length != this.length)) {
            return true
        }

        if (newText is Spanned) {
            return newText != this
        }

        for (i in newText.indices) {
            if (newText[i] != this[i]) {
                return true
            }
        }

        return false
    }
}