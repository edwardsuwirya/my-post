package com.enigmacamp.mysimpleretorfit.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.mysimpleretorfit.R
import com.enigmacamp.mysimpleretorfit.data.remote.RetrofitInstance
import com.enigmacamp.mysimpleretorfit.data.remote.UnsplashRetrofitInstance
import com.enigmacamp.mysimpleretorfit.data.remote.service.DownloadJobIntentService
import com.enigmacamp.mysimpleretorfit.repository.PostRepository
import com.enigmacamp.mysimpleretorfit.util.ResourceStatus

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        subscribe()

        viewModel.getPostById()
    }

    private fun initViewModel() {
        val dataSource = RetrofitInstance.postApi
        val unsplashApi = UnsplashRetrofitInstance.unsplashApi
        val repo = PostRepository(dataSource, unsplashApi, applicationContext)
        viewModel =
            ViewModelProvider(this, MainActivityViewModelFactory(repo)).get(
                MainViewModel::class.java
            )
    }

    private fun subscribe() {
        viewModel.postLiveData.observe(this, { it ->
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("JsonPlaceHolder", "Loading")
                ResourceStatus.SUCCESS -> {
                    Log.d("JsonPlaceHolder", "subscribe: ${it.data}")
                    val response = it.data
                    response?.let { postResponse ->
                        startJobIntentService(postResponse[0].fileUrl)
                    }

                }
                ResourceStatus.ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startJobIntentService(fileUrl: String) {
        val intent =
            Intent(this, DownloadJobIntentService::class.java)
        intent.putExtra("url", fileUrl)
        DownloadJobIntentService.enqueueWork(this, intent)
    }

}