package com.jesil.ghostguard.core.data

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityRepository @Inject constructor() {
    // The source of truth for the security state
    val securityState = MutableStateFlow(SecurityState.IDLE)

    fun updateState(value: SecurityState) {
        securityState.value = value
    }
}