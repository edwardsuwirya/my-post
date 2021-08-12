package com.enigmacamp.mysimpleretorfit.data.remote.service

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.JobIntentService
import com.enigmacamp.mysimpleretorfit.data.remote.UnsplashRetrofitInstance.unsplashApi
import com.enigmacamp.mysimpleretorfit.util.SaveToStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadJobIntentService : JobIntentService() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onHandleWork(intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "onHandleWork")
            val fileUrl = intent.getStringExtra("url")
            val imageResponseBody = unsplashApi.getImage(fileUrl!!)
            SaveToStorage.save(this@DownloadJobIntentService, imageResponseBody.body())
            val intent = Intent("com.enigmacamp.mysimpleservice.DOWNLOAD_BROADCAST")
            intent.putExtra("com.enigmacamp.mysimpleservice.EXTRA_TEXT", "Download Finish")
            sendBroadcast(intent)
        }
    }

    companion object {
        const val TAG = "JobIntentService"
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, DownloadJobIntentService::class.java, 123, work)
        }
    }
}