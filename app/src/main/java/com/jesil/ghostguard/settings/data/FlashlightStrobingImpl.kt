package com.jesil.ghostguard.settings.data

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.jesil.ghostguard.settings.domain.FlashlightStrobing
import com.jesil.ghostguard.settings.domain.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class FlashlightStrobingImpl(
    private val settingsRepository: SettingsRepository,
    @param:ApplicationContext private val context: Context,
) : FlashlightStrobing {

    private var strobeJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun startStrobeLightIfNeeded() {
        strobeJob?.cancel()

        strobeJob = scope.launch {
            val isStrobeEnabled = settingsRepository.getSecurityValues().first().flashlightStrobing

            if (!isStrobeEnabled) return@launch

            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                try {
                    cameraManager.getCameraCharacteristics(id)
                        .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
                } catch (e: Exception) {
                    false
                }
            } ?: return@launch // Exit if no flash is found

            try {
                while (isActive) {
                    cameraManager.setTorchMode(cameraId, true)
                    delay(100.milliseconds)
                    cameraManager.setTorchMode(cameraId, false)
                    delay(100.milliseconds)
                }
            } finally {
                try {
                    cameraManager.setTorchMode(cameraId, false)
                } catch (_: Exception) {
                }
            }
        }
    }

    override fun stopStrobeLight() {
        strobeJob?.cancel()
        strobeJob = null
    }
}