package com.jesil.ghostguard.settings.presntation.mapper

import com.jesil.ghostguard.settings.domain.SettingsValue
import com.jesil.ghostguard.settings.presntation.SecuritySettingsState

// to map values back to the domain model from settings UI
fun SecuritySettingsState.toSettingsValue(): SettingsValue = SettingsValue(
    threshold = motionThreshold,
    proximityDelaySec = proximityDelaySec,
    flashlightStrobing = flashlightStrobing,
    maxVolumeOverride = maxVolumeOverride,
    alarmTone = alarmTone,
    deviceAdminStatus = deviceAdminStatus,
    lockdownMode = lockdownMode
)