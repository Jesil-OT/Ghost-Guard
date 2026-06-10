package com.jesil.ghostguard.logs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.jesil.ghostguard.logs.presentation.model.LogEventType

@Entity(tableName = "security_logs")
data class SecurityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "log_type") val logType: LogEventType,
    @ColumnInfo(name = "timestamp") val timeStamp: Long
)

//class Converters {
//    @TypeConverter
//    fun fromLogEventType(type: LogEventType): String = type.name
//
//    @TypeConverter
//    fun toLogEventType(value: String): LogEventType = LogEventType.valueOf(value)
//}
