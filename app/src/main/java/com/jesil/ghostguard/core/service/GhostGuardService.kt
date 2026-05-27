package com.jesil.ghostguard.core.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.jesil.ghostguard.core.data.SecurityDataStore
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
    @Inject lateinit var securityDataStore: SecurityDataStore
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        sensorMonitor = SensorMonitor {
            Log.e(TAG, "Motion Detected!!!!, \uD83D\uDEA8 VALID SECURITY EVENT TRIGGERED! \uD83D\uDEA8")
           securityRepository.updateState(SecurityState.WARNING)
        }

        serviceScope.launch {
            securityRepository.securityState.collect { state ->
                when (state) {
                    SecurityState.IDLE, SecurityState.ARMED-> {
                       soundManager.stopSound()
                    }
                    SecurityState.WARNING -> {
                        soundManager.stopSound()
                        launchWarningMode()
                        startWatchdog()
                    }
                    SecurityState.ALARM -> soundManager.startSound()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ServiceActions.START_MOTION_DETECTION.toString() -> startMotionDetection()
            ServiceActions.START_POCKET_MODE.toString() -> {}
            ServiceActions.STOP.toString() -> {
                soundManager.stopSound()
                stopSelf()
            }
            ServiceActions.START_SOUND.toString() -> securityRepository.updateState(SecurityState.ALARM)
            ServiceActions.STOP_SOUND.toString() -> securityRepository.updateState(SecurityState.IDLE)

        }

        return START_STICKY
    }

    override fun onDestroy() {
        stopAllSensors()
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

    private fun startWatchdog(){
        serviceScope.launch {
            securityDataStore.isWarningActiveFLow.collect { isActive ->
                val currentState = securityRepository.securityState.value
                when {
                    currentState == SecurityState.WARNING && !isActive -> {
                        Log.e(TAG, "WATCHDOG: App was killed/backgrounded! in Warning Force re-launching...")
                        launchWarningMode()
                    }
                    currentState == SecurityState.ALARM && !isActive -> {
                        Log.e(TAG, "WATCHDOG: App was killed/backgrounded! in Alarmed Force re-launching...")
                        launchWarningMode()
                    }
                }
            }
        }
    }
    private fun launchWarningMode(){
//        val notification = NotificationHelper.createWarningNotification(this)
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.notify(1, notification)

        if (Settings.canDrawOverlays(this)) {
            val intent = Intent(this, WarningActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }
    }

    private fun stopAllSensors(){
        sensorMonitor?.let {
            it.resetMonitor()
            sensorManager.unregisterListener(it)
        }
    }
}