package com.jesil.ghostguard.core.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.jesil.ghostguard.core.sensors.SensorMonitor
import com.jesil.ghostguard.core.utils.NotificationHelper
import com.jesil.ghostguard.core.utils.SoundManager
import com.jesil.ghostguard.warning.WarningActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GhostGuardService: Service() {
    companion object{
        const val TAG = "GhostGuardService"
    }

    @Inject lateinit var sensorManager: SensorManager
    @Inject lateinit var soundManager: SoundManager
    private var sensorMonitor: SensorMonitor? = null

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "Service created!!!!")
        sensorMonitor = SensorMonitor {
            Log.e(TAG, "Motion Detected!!!!, \uD83D\uDEA8 VALID SECURITY EVENT TRIGGERED! \uD83D\uDEA8")
            launchWarningMode()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ServiceActions.START_MOTION_DETECTION.toString() -> startMotionDetection()
            ServiceActions.START_POCKET_MODE.toString() -> {}
            ServiceActions.STOP.toString() -> stopSelf()
            ServiceActions.START_SOUND.toString() -> soundManager.startSound()
            ServiceActions.STOP_SOUND.toString() -> soundManager.stopSound()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        sensorMonitor?.let {
            it.resetMonitor()
            sensorManager.unregisterListener(it)
        }
        soundManager.stopSound()
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

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null && sensorMonitor != null) {
            sensorManager.registerListener(
                sensorMonitor,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }
    private fun launchWarningMode(){
        val intent = Intent(this, WarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)

    }
}