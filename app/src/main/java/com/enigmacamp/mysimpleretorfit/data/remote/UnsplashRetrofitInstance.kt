package com.enigmacamp.mysimpleretorfit.data.remote

import com.enigmacamp.mysimpleretorfit.data.remote.api.UnsplashApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object UnsplashRetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://images.unsplash.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val unsplashApi by lazy {
        retrofit.create(UnsplashApi::class.java)
    }
}