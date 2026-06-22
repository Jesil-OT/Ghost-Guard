package com.jesil.ghostguard.settings.presntation

sealed interface SettingsEvent {
    data class UpdateMotionThreshold(val value: Float) : SettingsEvent
    data class UpdateProximityDelay(val seconds: Int) : SettingsEvent
    data class ToggleFlashlight(val enabled: Boolean) : SettingsEvent
    data class ToggleLockdownMode(val enabled: Boolean) : SettingsEvent
    data class UpdateAlarmTone(val tone: String) : SettingsEvent
    object AboutDeveloperClicked : SettingsEvent
}