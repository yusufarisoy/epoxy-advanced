package com.yusufarisoy.core.data.remote

import retrofit2.Response

abstract class BaseDataSource {

    suspend fun <T> sendRequest(networkCall: suspend () -> Response<T>): NetworkResponse<T> {
        try {
            val response = networkCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResponse.success(it)
                }
            }
            return error(exception = null, message = response.message())
        } catch (ex: Exception) {
            return error(exception = ex, message = ex.message)
        }
    }

    private fun <T> error(
        exception: Exception?,
        message: String?
    ): NetworkResponse<T> = NetworkResponse.error(exception, message)
}