package com.jesil.ghostguard.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataKeys {
    val IS_MOTION_DETECTION_ARMED = booleanPreferencesKey("is_motion_detection_armed")
    val IS_POCKET_MODE_ENABLED = booleanPreferencesKey("is_pocket_mode_enabled")
    val IS_WARNING_ACTIVE = booleanPreferencesKey("is_warning_active")
    val THRESHOLD = floatPreferencesKey("threshold")
    val PROXIMITY_DELAY_SEC = floatPreferencesKey("proximity_delay_sec")
    val FLASHLIGHT_STROBING = booleanPreferencesKey("flashlight_strobing")
    val MAX_VOLUME_OVERRIDE = booleanPreferencesKey("max_volume_override")
    val ALARM_TONE = stringPreferencesKey("alarm_tone")
    val DEVICE_ADMIN_STATUS = booleanPreferencesKey("device_admin_status")
    val LOCKDOWN_MODE = booleanPreferencesKey("lockdown_mode")

}