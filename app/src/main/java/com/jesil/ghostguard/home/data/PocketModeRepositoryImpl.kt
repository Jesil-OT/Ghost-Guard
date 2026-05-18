package com.jesil.ghostguard.home.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.core.utils.DataKeys
import com.jesil.ghostguard.home.domain.PocketModeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PocketModeRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PocketModeRepository {

    override val isPocketModeEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DataKeys.IS_POCKET_MODE_ENABLED] == true
        }

    override suspend fun setPocketModeEnabled(isEnabled: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.IS_POCKET_MODE_ENABLED] = isEnabled
            }
        }
    }

}