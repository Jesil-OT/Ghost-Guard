package com.jesil.ghostguard.logs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesil.ghostguard.logs.data.model.SecurityLogEntity
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import kotlinx.coroutines.flow.Flow

@Dao
interface SecurityLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SecurityLogEntity)

    @Query("SELECT * FROM security_logs WHERE log_type IN (:logType) ORDER BY timestamp DESC")
    fun getLogsByType(logType: List<LogEventType>): Flow<List<SecurityLogEntity>>

    @Query("DELETE FROM security_logs WHERE id = :logId")
    suspend fun deleteLogById(logId: Int)

    @Query("DELETE FROM security_logs")
    suspend fun deleteAllLogs()
}