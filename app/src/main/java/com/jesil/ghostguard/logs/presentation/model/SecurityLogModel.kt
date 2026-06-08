package com.jesil.ghostguard.logs.presentation.model

data class SecurityLogModel(
    val id: Int,
    val title: String,
    val description: String,
    val timeStamp: String,
    val type: LogEventType,
)

data class GroupedLogModel(
    val day: String,
    val securityLogs: List<SecurityLogModel>
)