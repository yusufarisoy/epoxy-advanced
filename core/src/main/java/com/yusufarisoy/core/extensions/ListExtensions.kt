package com.yusufarisoy.core.extensions

fun <T> List<T>.addElements(newList: List<T>?) = toMutableList().apply {
    newList?.let {
        addAll(it)
    }
}