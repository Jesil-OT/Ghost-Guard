package com.jesil.ghostguard.logs.domain.mapper

import com.jesil.ghostguard.logs.data.model.SecurityLogEntity
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title

fun SecurityLogEntity.toDomain(): SecurityLog {
    return SecurityLog(
        id = id,
        title = logType.title(),
        description = logType.description(),
        timeStamp = timeStamp,
        type = logType
    )
}