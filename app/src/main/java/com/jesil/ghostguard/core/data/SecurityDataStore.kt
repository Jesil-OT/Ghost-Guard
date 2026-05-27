package com.jesil.ghostguard.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.core.utils.DataKeys
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val isWarningActiveFLow = dataStore.data.map {
        it[DataKeys.IS_WARNING_ACTIVE] ?: false
    }

    suspend fun setWarningActive(isActive: Boolean){
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[DataKeys.IS_WARNING_ACTIVE] = isActive
            }
        }
    }
}