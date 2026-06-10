package com.jesil.ghostguard.logs.data.mapper

import com.jesil.ghostguard.logs.data.model.SecurityLogEntity
import com.jesil.ghostguard.logs.domain.SecurityLog

fun SecurityLog.toEntity(): SecurityLogEntity {
    return SecurityLogEntity(
        logType = type,
        timeStamp = timeStamp,
    )
}
