package com.jesil.ghostguard.logs.domain

import com.jesil.ghostguard.logs.presentation.model.LogEventType
import kotlinx.coroutines.flow.Flow

interface SecurityLogRepository {

    suspend fun addLog(log: SecurityLog)

    fun getLogsByType(logType: List<LogEventType>): Flow<List<SecurityLog>>

    suspend fun deleteLogById(logId: Int)

    suspend fun deleteAllLogs()

}