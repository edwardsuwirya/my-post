package com.enigmacamp.mysimpleretorfit.repository

import android.util.Log
import com.enigmacamp.mysimpleretorfit.data.remote.api.PostApi
import com.enigmacamp.mysimpleretorfit.data.remote.request.PostRequest
import com.enigmacamp.mysimpleretorfit.data.remote.response.BaseResponse
import com.enigmacamp.mysimpleretorfit.data.remote.response.PostResponse
import kotlinx.coroutines.withTimeout

class PostRepository(private val postApi: PostApi) {
    suspend fun getPosts(): BaseResponse<PostResponse>? {
        return try {
            withTimeout(2000) {
                val response = postApi.getPosts()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("PostRepository", e.localizedMessage)
            null
        }
    }

    suspend fun getPostById(id: Int) = postApi.getPostById(id)
    suspend fun savePost(post: PostRequest) = postApi.createPost(post)
}