package com.yusufarisoy.core.utils

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextWatcher : TextWatcher {
    fun onTextChanged(text: String?)
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged(p0?.toString())
    }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    override fun afterTextChanged(p0: Editable?) { }
}