package com.enigmacamp.mysimpleretorfit

import android.app.Application
import android.content.IntentFilter
import com.enigmacamp.mysimpleretorfit.data.remote.broadcastReceiver.DownloadBroadcastReceiver

class BaseApplication : Application() {
    private val downloadBroadcastReceiver by lazy { DownloadBroadcastReceiver() }
    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter("com.enigmacamp.mysimpleservice.DOWNLOAD_BROADCAST")
        registerReceiver(downloadBroadcastReceiver, intentFilter)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(downloadBroadcastReceiver)
    }

    companion object {
        const val CHANNEL_ID = "DownloadChannel"
    }
}