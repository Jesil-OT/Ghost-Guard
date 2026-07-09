package com.jesil.ghostguard.settings.presntation

sealed interface SettingsEvent {
    data class UpdateMotionThreshold(val value: Float) : SettingsEvent
    data class UpdateProximityDelay(val seconds: Float) : SettingsEvent
    data class ToggleFlashlight(val enabled: Boolean) : SettingsEvent
    data class ToggleMaxOverride(val enabled: Boolean) : SettingsEvent
    data class UpdateAlarmTone(val tone: String) : SettingsEvent
    data class ToggleDeviceAdminStatus(val enabled: Boolean) : SettingsEvent
    data class ToggleLockdownMode(val enabled: Boolean) : SettingsEvent
}