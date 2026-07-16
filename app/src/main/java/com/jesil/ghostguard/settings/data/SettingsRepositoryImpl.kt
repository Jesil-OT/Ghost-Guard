package com.jesil.ghostguard.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.core.utils.DataKeys
import com.jesil.ghostguard.settings.domain.SettingsRepository
import com.jesil.ghostguard.settings.domain.SettingsValue
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override fun getSecurityValues(): Flow<SettingsValue> =
        dataStore.data.map { value ->
            SettingsValue(
                threshold = value[DataKeys.THRESHOLD] ?: 0f,
                proximityDelaySec = value[DataKeys.PROXIMITY_DELAY_SEC] ?: 0f,
                flashlightStrobing = value[DataKeys.FLASHLIGHT_STROBING] ?: false,
                maxVolumeOverride = value[DataKeys.MAX_VOLUME_OVERRIDE] ?: false,
                alarmTone = value[DataKeys.ALARM_TONE] ?: "",
                deviceAdminStatus = value[DataKeys.DEVICE_ADMIN_STATUS] ?: false,
                lockdownMode = value[DataKeys.LOCKDOWN_MODE] ?: false
            )
        }

    override suspend fun updateThreshold(value: Float) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.THRESHOLD] = value
            }
        }
    }

    override suspend fun updateProximityDelay(value: Float) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.PROXIMITY_DELAY_SEC] = value
            }
        }
    }

    override suspend fun updateFlashlight(value: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.FLASHLIGHT_STROBING] = value
            }
        }
    }

    override suspend fun updateMaxVolumeOverride(value: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.MAX_VOLUME_OVERRIDE] = value
            }
        }
    }

    override suspend fun updateAlarmTone(value: String) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.ALARM_TONE] = value
            }
        }
    }

    override suspend fun updateDeviceAdminStatus(value: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.DEVICE_ADMIN_STATUS] = value
            }
        }
    }

    override suspend fun updateLockdownMode(value: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.LOCKDOWN_MODE] = value
            }
        }
    }

}