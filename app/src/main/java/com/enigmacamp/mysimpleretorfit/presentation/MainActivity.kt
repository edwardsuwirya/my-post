package com.enigmacamp.mysimpleretorfit.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.mysimpleretorfit.R
import com.enigmacamp.mysimpleretorfit.data.remote.RetrofitInstance
import com.enigmacamp.mysimpleretorfit.repository.PostRepository
import com.enigmacamp.mysimpleretorfit.util.ResourceStatus

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        subscribe()
        viewModel.getPosts()
    }

    private fun initViewModel() {
        val dataSource = RetrofitInstance.postApi
        val repo = PostRepository(dataSource)
        viewModel =
            ViewModelProvider(this, MainActivityViewModelFactory(repo)).get(
                MainViewModel::class.java
            )
    }

    private fun subscribe() {
        viewModel.postLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("JsonPlaceHolder", "Loading")
                ResourceStatus.SUCCESS -> Log.d("JsonPlaceHolder", "subscribe: ${it.data}")
                ResourceStatus.ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}