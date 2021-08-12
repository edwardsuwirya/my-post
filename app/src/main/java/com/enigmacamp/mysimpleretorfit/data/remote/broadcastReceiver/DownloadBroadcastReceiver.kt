package com.enigmacamp.mysimpleretorfit.data.remote.broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.enigmacamp.mysimpleretorfit.BaseApplication
import com.enigmacamp.mysimpleretorfit.R

class DownloadBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.let { NotificationManagerCompat.from(it) }

        if ("com.enigmacamp.mysimpleservice.DOWNLOAD_BROADCAST".equals(intent?.action)) {
            val stringText = intent?.getStringExtra("com.enigmacamp.mysimpleservice.EXTRA_TEXT")
            createNotificationChannel(notificationManager!!)
            doNotif(context, notificationManager, stringText ?: "")
        }
    }

    private fun createNotificationChannel(manager: NotificationManagerCompat) {
        val downloadNotificationChannel = NotificationChannel(
            BaseApplication.CHANNEL_ID,
            "Download Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        downloadNotificationChannel.description = "Download"
        manager.createNotificationChannel(downloadNotificationChannel)
    }

    private fun doNotif(context: Context, manager: NotificationManagerCompat, message: String) {
        val notification = NotificationCompat.Builder(context, BaseApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle("Download Notification")
            .setContentText(message)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        manager.notify(1, notification)


    }
}