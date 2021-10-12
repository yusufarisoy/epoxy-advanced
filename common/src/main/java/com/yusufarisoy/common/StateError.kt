package com.yusufarisoy.common

data class StateError(
    var exception: Exception? = null,
    var message: String? = null
)