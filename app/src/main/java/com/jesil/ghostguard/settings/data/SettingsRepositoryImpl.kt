package com.jesil.ghostguard.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.core.utils.DataKeys
import com.jesil.ghostguard.settings.domain.repository.SettingsRepository
import com.jesil.ghostguard.settings.domain.repository.SettingsValue
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

    override suspend fun updateSecurityValues(values: SettingsValue) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.THRESHOLD] = values.threshold
                this[DataKeys.PROXIMITY_DELAY_SEC] = values.proximityDelaySec
                this[DataKeys.FLASHLIGHT_STROBING] = values.flashlightStrobing
                this[DataKeys.MAX_VOLUME_OVERRIDE] = values.maxVolumeOverride
                this[DataKeys.ALARM_TONE] = values.alarmTone
                this[DataKeys.DEVICE_ADMIN_STATUS] = values.deviceAdminStatus
                this[DataKeys.LOCKDOWN_MODE] = values.lockdownMode
            }
        }
    }
}