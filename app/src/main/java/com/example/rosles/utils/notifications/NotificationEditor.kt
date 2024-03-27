package com.example.rosles.utils.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationEditor(private val context: Context, private val CHANNEL_ID: String) {
    private var notificationBuilderBuffer:  NotificationCompat.Builder? = null

    public fun createNotification(
        smallIcon: Int,
        title: String,
        test: String,
        pendingIntent: PendingIntent
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        notificationBuilderBuffer = notificationBuilder

        return notificationBuilder
            .setContentText(test)
            .build()
    }

    public fun editTestNotification(newTest: String): Notification? {
        return notificationBuilderBuffer?.let {
            val editedNotification = it
            editedNotification
                .setContentText(newTest)
                .build()
        }
    }

    public fun createNotificationChannel(
        channelName: String,
        channelDescription: String
    ): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
        }
        return channel
    }
}