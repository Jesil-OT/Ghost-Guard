package com.jesil.ghostguard.logs.domain

import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title
import kotlinx.coroutines.flow.Flow

interface SecurityLogRepository {

    suspend fun addLog(log: SecurityLog)

    fun getLogsByType(logType: List<LogEventType>): Flow<List<SecurityLog>>

    suspend fun deleteAllLogs()

    suspend fun addLog(type: LogEventType) {
        addLog(
            SecurityLog(
                title = type.title(),
                description = type.description(),
                timeStamp = System.currentTimeMillis(),
                type = type,
            )
        )
    }

}