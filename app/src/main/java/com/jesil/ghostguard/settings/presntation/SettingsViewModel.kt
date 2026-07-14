package com.jesil.ghostguard.settings.presntation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SecuritySettingsState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SecuritySettingsState()
    )


    fun onAction(action: SettingsEvent) {
        when (action) {
            is SettingsEvent.UpdateMotionThreshold -> {
                _state.update {
                    it.copy(
                        motionThreshold = action.value,
                        motionThresholdLabel = if (action.value < 3f) "Low" else "High"
                    )
                }
            }

            is SettingsEvent.UpdateProximityDelay -> {
                _state.update {
                    it.copy(
                        proximityDelaySec = action.seconds,
                        proximityDelayLabel = action.seconds.proximityDelayLabel()
                    )
                }
            }

            is SettingsEvent.ToggleFlashlight -> {
                _state.update { it.copy(flashlightStrobing = action.enabled) }
            }

            is SettingsEvent.ToggleMaxOverride -> {
                _state.update { it.copy(maxVolumeOverride = action.enabled) }
            }

            is SettingsEvent.UpdateAlarmTone -> {
                _state.update { it.copy(alarmTone = action.tone) }
            }

            is SettingsEvent.ToggleDeviceAdminStatus -> {
                _state.update { it.copy(deviceAdminStatus = action.enabled) }
            }

            is SettingsEvent.ToggleLockdownMode -> {
                _state.update { it.copy(lockdownMode = action.enabled) }
            }
        }
    }

    private fun Float.proximityDelayLabel(): String =
        when (this) {
            0.1f -> "1s"
            0.2f -> "2s"
            0.3f -> "3s"
            0.4f -> "4s"
            0.5f -> "5s"
            0.6f -> "6s"
            0.7f -> "7s"
            0.8f -> "8s"
            else -> "9s"
        }
}