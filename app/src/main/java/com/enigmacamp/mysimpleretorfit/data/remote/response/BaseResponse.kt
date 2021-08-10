package com.enigmacamp.mysimpleretorfit.data.remote.response

data class BaseResponse<T>(
    val status: String, val message: String, val data: List<T>
)

