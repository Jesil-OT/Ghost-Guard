package com.jesil.ghostguard.settings.presntation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.settings.domain.SettingsRepository
import com.jesil.ghostguard.settings.presntation.mapper.toSettingsValue
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    companion object {
        const val THRESHOLD_SENSITIVITY = 5.5f
    }

    val state = settingsRepository.getSecurityValues()
        .map {
            SecuritySettingsState(
                motionThreshold = it.threshold,
                proximityDelaySec = it.proximityDelaySec,
                flashlightStrobing = it.flashlightStrobing,
                maxVolumeOverride = it.maxVolumeOverride,
                alarmTone = it.alarmTone,
                deviceAdminStatus = it.deviceAdminStatus,
                lockdownMode = it.lockdownMode
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SecuritySettingsState()
        )


    fun onAction(action: SettingsEvent) {
        when (action) {
            is SettingsEvent.UpdateMotionThreshold -> {
                viewModelScope.launch {
//                    val threshold = THRESHOLD_SENSITIVITY - action.value
                    settingsRepository.updateThreshold(action.value)
                }
            }

            is SettingsEvent.UpdateProximityDelay -> {
                viewModelScope.launch {
                    settingsRepository.updateProximityDelay(action.seconds)
                }
            }

            is SettingsEvent.ToggleFlashlight -> {
                viewModelScope.launch {
                    settingsRepository.updateFlashlight(action.enabled)
                }
            }

            is SettingsEvent.ToggleMaxOverride -> {
                viewModelScope.launch {
                    settingsRepository.updateMaxVolumeOverride(action.enabled)
                }
            }

            is SettingsEvent.UpdateAlarmTone -> {
                viewModelScope.launch {
                    settingsRepository.updateAlarmTone(action.tone)
                }
            }

            is SettingsEvent.ToggleDeviceAdminStatus -> {
                viewModelScope.launch {
                    settingsRepository.updateDeviceAdminStatus(action.enabled)
                }
            }

            is SettingsEvent.ToggleLockdownMode -> {
                viewModelScope.launch {
                    settingsRepository.updateLockdownMode(action.enabled)
                }
            }
        }
    }
}
