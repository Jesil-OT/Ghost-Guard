package com.jesil.ghostguard.settings.presntation

data class SecuritySettingsState(
    // Detection Sensitivity
    val motionThreshold: Float = 0.8f,
    val motionThresholdLabel: String = "High",
    val proximityDelaySec: Int = 3,

    // Alarm Preferences
    val flashlightStrobing: Boolean = false,
    val maxVolumeOverride: Boolean = false,
    val alarmTone: String = "Tactical Siren",

    // Security Protocols
    val deviceAdminStatus: Boolean = false,
    val lockdownMode: Boolean = false
)
