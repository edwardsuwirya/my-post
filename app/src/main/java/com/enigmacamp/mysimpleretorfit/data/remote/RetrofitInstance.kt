package com.enigmacamp.mysimpleretorfit.data.remote

import com.enigmacamp.mysimpleretorfit.BuildConfig.BASE_URL
import com.enigmacamp.mysimpleretorfit.data.remote.api.PostApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val postApi by lazy {
        retrofit.create(PostApi::class.java)
    }
}