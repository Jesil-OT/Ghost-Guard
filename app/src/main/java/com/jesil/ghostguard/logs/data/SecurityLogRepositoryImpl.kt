package com.jesil.ghostguard.logs.data

import com.jesil.ghostguard.logs.data.mapper.toEntity
import com.jesil.ghostguard.logs.data.model.SecurityLogEntity
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.domain.mapper.toDomain
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SecurityLogRepositoryImpl @Inject constructor(
    private val securityLogDao: SecurityLogDao
): SecurityLogRepository {
    override suspend fun addLog(log: SecurityLog) {
        securityLogDao.insertLog(log.toEntity())
    }

    override fun getLogsByType(logType: List<LogEventType>): Flow<List<SecurityLog>> = flow {
        securityLogDao.getLogsByType(logType).collect { logs ->
            emit(logs.map { it.toDomain() })
        }
    }

    override suspend fun deleteLogById(logId: Int) {
//        TODO("Not yet implemented")
    }

    override suspend fun deleteAllLogs() {
//        TODO("Not yet implemented")
    }

}