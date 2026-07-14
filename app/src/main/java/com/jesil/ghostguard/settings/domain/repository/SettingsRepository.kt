package com.jesil.ghostguard.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSecurityValues(): Flow<SettingsValue>

    suspend fun updateSecurityValues(values: SettingsValue)
}