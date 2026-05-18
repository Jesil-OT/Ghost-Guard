package com.jesil.ghostguard.home.domain

import kotlinx.coroutines.flow.Flow

interface MotionDetectionRepository {
    val isArmed: Flow<Boolean>
    suspend fun setArmed(isArmed: Boolean)
}
