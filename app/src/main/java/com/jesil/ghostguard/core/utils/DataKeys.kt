package com.jesil.ghostguard.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object DataKeys {
    val IS_MOTION_DETECTION_ARMED = booleanPreferencesKey("is_motion_detection_armed")
    val IS_POCKET_MODE_ENABLED = booleanPreferencesKey("is_pocket_mode_enabled")
    val IS_WARNING_ACTIVE = booleanPreferencesKey("is_warning_active")
}