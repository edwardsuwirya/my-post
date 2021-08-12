package com.enigmacamp.mysimpleretorfit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val downloadNotificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Download Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        downloadNotificationChannel.description = "Download"
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(downloadNotificationChannel)
    }

    companion object {
        const val CHANNEL_ID = "DownloadChannel"
    }
}