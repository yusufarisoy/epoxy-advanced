package com.yusufarisoy.core.views.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun showProgress()

    abstract fun hideProgress()
}