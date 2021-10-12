package com.yusufarisoy.core.data.remote

import com.yusufarisoy.common.StateError
import java.lang.Exception

data class NetworkResponse<out T>(
    val status: Status,
    val data: T? = null,
    val error: StateError?
)
{
    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): NetworkResponse<T> = NetworkResponse(
            status = Status.SUCCESS,
            data = data,
            error = null
        )

        fun <T> error(
            exception: Exception?,
            message: String?
        ): NetworkResponse<T> = NetworkResponse(
            status = Status.ERROR,
            data = null,
            error = StateError(exception, message)
        )
    }
}