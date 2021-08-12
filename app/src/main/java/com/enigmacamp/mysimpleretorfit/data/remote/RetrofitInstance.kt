package com.enigmacamp.mysimpleretorfit.data.remote

import android.util.Log
import com.enigmacamp.mysimpleretorfit.BuildConfig.BASE_URL
import com.enigmacamp.mysimpleretorfit.data.remote.api.PostApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private fun authTokenInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder =
            originalRequest.newBuilder().header("Authorization", "AuthTokenInvalid")
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private fun errorInterceptor() = Interceptor {
        val originalRequest = it.request()
        val response = it.proceed(originalRequest)
        when (response.code()) {
            401 -> Log.e("OkHttpClient", "errorInterceptor: Unauthorized ")
            404 -> Log.e("OkHttpClient", "errorInterceptor: Page Not Found ")
            500 -> Log.e("OkHttpClient", "errorInterceptor: Internal Server Error ")
        }
        return@Interceptor response
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor {
                Log.d("OkHttpClient", "Print me")
                return@addInterceptor it.proceed(it.request())
            }
            .addInterceptor(authTokenInterceptor())
            .addInterceptor(errorInterceptor())
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val postApi by lazy {
        retrofit.create(PostApi::class.java)
    }
}