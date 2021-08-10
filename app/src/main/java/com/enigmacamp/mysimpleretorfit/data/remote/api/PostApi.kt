package com.enigmacamp.mysimpleretorfit.data.remote.api

import com.enigmacamp.mysimpleretorfit.data.remote.request.PostRequest
import com.enigmacamp.mysimpleretorfit.data.remote.response.BaseResponse
import com.enigmacamp.mysimpleretorfit.data.remote.response.PostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): Response<BaseResponse<PostResponse>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<PostResponse>

    @POST("posts")
    suspend fun createPost(@Body post: PostRequest): Response<PostResponse>
}