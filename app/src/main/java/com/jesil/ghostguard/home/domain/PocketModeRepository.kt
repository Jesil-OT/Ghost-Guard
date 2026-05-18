package com.jesil.ghostguard.home.domain

import kotlinx.coroutines.flow.Flow

interface PocketModeRepository {
    val isPocketModeEnabled: Flow<Boolean>
    suspend fun setPocketModeEnabled(isEnabled: Boolean)
}