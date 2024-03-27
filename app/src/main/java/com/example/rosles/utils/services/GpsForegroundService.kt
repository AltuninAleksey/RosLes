package com.example.rosles.utils.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.rosles.R
import com.example.rosles.Screens.Authorization
import com.example.rosles.Screens.SaveState
import com.example.rosles.Screens.SaveState.saveGpsManager
import com.example.rosles.utils.gps.simpleframework.ParseGps
import com.example.rosles.utils.notifications.NotificationEditor
import kotlinx.coroutines.*

class GpsForegroundService: Service() {
    private val CHANNEL_ID = "gpsForeground"
    private val NOTIFICATION_ID = 1
    private val notificationEditor = NotificationEditor(this, CHANNEL_ID)

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createChannel()
        sendNotificationForStartForeground()

        SaveState.gpsCoroutine = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(5000)
                saveGpsManager?.updateLocation()
                val waypoint = ParseGps.Waypoint()
                waypoint.lon = saveGpsManager!!.longitude
                waypoint.lat = saveGpsManager!!.latitude
                SaveState.listWaypoint.add(waypoint)
            }
        }

        return START_NOT_STICKY
    }

    private fun createChannel() {
        val channel = notificationEditor.createNotificationChannel(
            "RosLesGPS",
            "RosLes GPS tracking"
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE)
        if (notificationManager is NotificationManager) {
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun createStartNotification(): Notification {
        val intent = Intent(this, Authorization::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = notificationEditor.createNotification(
            R.drawable.location,//КОСТЫЛЬ ....................................
            "RosLes gps tracking",
            "Идёт запись вашего пути.",
            pendingIntent
        )
        return notification
    }

    private fun sendNotificationForStartForeground() {
        startForeground(
            NOTIFICATION_ID,
            createStartNotification()
        )
    }
}