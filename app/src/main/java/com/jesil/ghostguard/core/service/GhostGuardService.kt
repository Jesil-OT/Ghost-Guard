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
import com.jesil.ghostguard.core.data.SecurityRepository
import com.jesil.ghostguard.core.data.SecurityState
import com.jesil.ghostguard.core.sensors.SensorMonitor
import com.jesil.ghostguard.core.utils.NotificationHelper
import com.jesil.ghostguard.core.utils.SoundManager
import com.jesil.ghostguard.warning.WarningActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GhostGuardService: Service() {
    companion object{
        const val TAG = "GhostGuardService"
    }
    @Inject lateinit var sensorManager: SensorManager
    @Inject lateinit var soundManager: SoundManager
    private var sensorMonitor: SensorMonitor? = null

    @Inject lateinit var securityRepository: SecurityRepository
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "Service created!!!!")
        sensorMonitor = SensorMonitor {
            Log.e(TAG, "Motion Detected!!!!, \uD83D\uDEA8 VALID SECURITY EVENT TRIGGERED! \uD83D\uDEA8")
           securityRepository.updateState(SecurityState.WARNING)
        }

        serviceScope.launch {
            securityRepository.securityState.collect { state ->
                when (state) {
                    SecurityState.IDLE -> {
                        Log.e(TAG, "Security State: IDLE")
                        soundManager.stopSound()
                    }
                    SecurityState.ARMED -> {
                        Log.e(TAG, "Security State: ARMED")
                    }
                    SecurityState.WARNING -> {
                        Log.e(TAG, "Security State: WARNING")
                        launchWarningMode()
                    }
                    SecurityState.ALARM -> {
                        Log.e(TAG, "Security State: ALARM")
                        soundManager.startSound()
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ServiceActions.START_MOTION_DETECTION.toString() -> startMotionDetection()
            ServiceActions.START_POCKET_MODE.toString() -> {}
            ServiceActions.STOP.toString() -> stopSelf()
            ServiceActions.START_SOUND.toString() -> {
//                soundManager.startSound()
                securityRepository.updateState(SecurityState.ALARM)
            }
            ServiceActions.STOP_SOUND.toString() -> {
//                soundManager.stopSound()
                securityRepository.updateState(SecurityState.IDLE)
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        stopAllSensors()
//        soundManager.stopSound()
        securityRepository.updateState(SecurityState.IDLE)
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
        securityRepository.updateState(SecurityState.ARMED)
    }
    private fun launchWarningMode(){
        val intent = Intent(this, WarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

    private fun stopAllSensors(){
        sensorMonitor?.let {
            it.resetMonitor()
            sensorManager.unregisterListener(it)
        }
    }
}