package com.enigmacamp.mysimpleretorfit.data.remote.request

data class PostRequest(
    val title: String,
    val userId: Int,
    val body: String
)