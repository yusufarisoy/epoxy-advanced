package com.yusufarisoy.rickandmorty.util

import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.core.views.base.BaseFragment

fun BaseFragment.hideAndShow(fragment: BaseFragment) {
    val hideIndex = if (parentFragmentManager.fragments.size > 2) {
        parentFragmentManager.fragments.size - 1
    } else {
        0
    }
    parentFragmentManager.beginTransaction()
        .hide(parentFragmentManager.fragments[hideIndex])
        .addToBackStack(null)
        .add(R.id.nav_host_fragment, fragment)
        .commit()
}