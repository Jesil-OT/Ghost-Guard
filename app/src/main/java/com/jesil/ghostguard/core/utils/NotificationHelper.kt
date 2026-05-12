package com.jesil.ghostguard.core.utils

import com.jesil.ghostguard.MainActivity
import com.jesil.ghostguard.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    private const val CHANNEL_ID = "ghost_guard_channel"
    private const val CHANNEL_NAME = "Ghost Guard Service"
    private const val NOTIFICATION_ID = 1

    fun createNotification(context: Context): Notification {
        // 1. Create the channel first (only needs to happen once)
        createNotificationChannel(context)

        // 2. Create an Intent to open the app when the notification is clicked
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // 3. Build the actual notification
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Ghost Guard Active")
            .setContentText("Monitoring sensors for movement...")
            .setSmallIcon(R.drawable.outline_security)
            .setOngoing(true) // Makes it harder for the user to swipe away
            .setPriority(NotificationCompat.PRIORITY_LOW) // Use LOW to avoid annoying pops
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Used for the Ghost Guard anti-theft service"
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}