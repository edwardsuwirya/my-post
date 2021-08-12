package com.enigmacamp.mysimpleretorfit.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import com.enigmacamp.mysimpleretorfit.data.remote.api.PostApi
import com.enigmacamp.mysimpleretorfit.data.remote.api.UnsplashApi
import com.enigmacamp.mysimpleretorfit.data.remote.request.PostRequest
import com.enigmacamp.mysimpleretorfit.data.remote.response.BaseResponse
import com.enigmacamp.mysimpleretorfit.data.remote.response.PostResponse
import com.enigmacamp.mysimpleretorfit.util.SaveToStorage
import kotlinx.coroutines.withTimeout
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PostRepository(
    private val postApi: PostApi,
    private val unsplashApi: UnsplashApi,
    private val context: Context
) {
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

    suspend fun getPostById(id: Int): BaseResponse<PostResponse>? {
        return try {
            withTimeout(10000) {
                val response = postApi.getPostById(id)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val listResponse = responseBody.data
                        val postResponse = listResponse[0]

                        responseBody
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("PostRepository", e.localizedMessage)
            null
        }
    }

    suspend fun savePost(post: PostRequest) = postApi.createPost(post)


}