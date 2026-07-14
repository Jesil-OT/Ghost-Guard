package com.jesil.ghostguard.settings.domain.repository

data class SettingsValue(
    val threshold: Float,
    val proximityDelaySec: Float,
    val flashlightStrobing: Boolean,
    val maxVolumeOverride: Boolean,
    val alarmTone: String,
    val deviceAdminStatus: Boolean,
    val lockdownMode: Boolean
)
