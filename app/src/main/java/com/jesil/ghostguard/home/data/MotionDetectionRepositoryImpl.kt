package com.jesil.ghostguard.home.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.core.utils.DataKeys
import com.jesil.ghostguard.home.domain.MotionDetectionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MotionDetectionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): MotionDetectionRepository {

    override val isArmed: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DataKeys.IS_MOTION_DETECTION_ARMED] == true
        }

    override suspend fun setArmed(isArmed: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.IS_MOTION_DETECTION_ARMED] = isArmed
            }
        }
    }

}