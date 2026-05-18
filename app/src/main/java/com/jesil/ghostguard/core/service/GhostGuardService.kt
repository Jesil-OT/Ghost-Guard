package com.jesil.ghostguard.core.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.jesil.ghostguard.core.utils.NotificationHelper

class GhostGuardService: Service() {

    companion object{
        const val TAG = "GhostGuardService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("GhostGuard", "Service created!!!!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ServiceActions.START_MOTION_DETECTION.toString() -> startMotionDetection()
            ServiceActions.START_POCKET_MODE.toString() -> {}
            ServiceActions.STOP.toString() -> stopSelf()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startMotionDetection(){
        val notification = NotificationHelper.createNotification(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            startForeground(
                1,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(1, notification)
        }
        Log.e(TAG, "Motion Detection Notification!!!")
    }
}