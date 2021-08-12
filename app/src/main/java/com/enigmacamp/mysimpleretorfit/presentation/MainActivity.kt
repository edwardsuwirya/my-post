package com.enigmacamp.mysimpleretorfit.presentation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.mysimpleretorfit.BaseApplication
import com.enigmacamp.mysimpleretorfit.R
import com.enigmacamp.mysimpleretorfit.data.remote.RetrofitInstance
import com.enigmacamp.mysimpleretorfit.data.remote.UnsplashRetrofitInstance
import com.enigmacamp.mysimpleretorfit.repository.PostRepository
import com.enigmacamp.mysimpleretorfit.util.ResourceStatus

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var notificationManager: NotificationManagerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        subscribe()
        notificationManager = NotificationManagerCompat.from(this)
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
        viewModel.postLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("JsonPlaceHolder", "Loading")
                ResourceStatus.SUCCESS -> {
                    Log.d("JsonPlaceHolder", "subscribe: ${it.data}")
                    doNotif()
                }
                ResourceStatus.ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun doNotif() {
        val notifyIntent = Intent(this, MainActivity::class.java)
        val notifyPendingIntent =
            PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, BaseApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle("Download Notification")
            .setContentText("Download complete")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(notifyPendingIntent)
            .build()
        notificationManager.notify(1, notification)


    }
}