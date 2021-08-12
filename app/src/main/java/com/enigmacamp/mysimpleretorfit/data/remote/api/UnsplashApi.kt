package com.enigmacamp.mysimpleretorfit.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UnsplashApi {
    @GET
    suspend fun getImage(@Url fileUrl: String): Response<ResponseBody>
}