package com.jesil.ghostguard.settings.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSecurityValues(): Flow<SettingsValue>

    suspend fun updateThreshold(value: Float)

    suspend fun updateProximityDelay(value: Float)

    suspend fun updateFlashlight(value: Boolean)

    suspend fun updateMaxVolumeOverride(value: Boolean)

    suspend fun updateAlarmTone(value: String)

    suspend fun updateDeviceAdminStatus(value: Boolean)

    suspend fun updateLockdownMode(value: Boolean)

}