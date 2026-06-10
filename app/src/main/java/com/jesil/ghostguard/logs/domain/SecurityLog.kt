package com.jesil.ghostguard.logs.domain

import com.jesil.ghostguard.logs.presentation.model.LogEventType

data class SecurityLog(
    val id: Int = 0,
    val title: String,
    val description: String,
    val timeStamp: Long,
    val type: LogEventType,
)