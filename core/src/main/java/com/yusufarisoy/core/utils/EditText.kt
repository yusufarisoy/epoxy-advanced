package com.yusufarisoy.core.utils

import android.text.Spanned
import com.google.android.material.textfield.TextInputEditText

fun setInputText(editText: TextInputEditText, text: CharSequence?) {
    if (setText(editText, text)) {
        editText.setSelection(editText.length())
    }
}

fun setText(editText: TextInputEditText, text: CharSequence?): Boolean {
    if (isTextDifferent(text, editText.text)) {
        editText.setText(text)
        return true
    }

    return false
}

fun isTextDifferent(text1: CharSequence?, text2: CharSequence?): Boolean {
    if (text1 == text2) {
        return false
    }

    if ((text1 == null || text2 == null) || (text1.length != text2.length)) {
        return true
    }

    if (text1 is Spanned) {
        return text1 != text2
    }

    for (i in text1.indices) {
        if (text1[i] != text2[i]) {
            return true
        }
    }

    return false
}