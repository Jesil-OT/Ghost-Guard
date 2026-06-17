package com.jesil.ghostguard.logs.presentation.mapper

import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.utils.toNormalTime

fun SecurityLog.toModel(): SecurityLogModel {
    return SecurityLogModel(
        id = id,
        title = title,
        description = description,
        timeStamp = timeStamp.toNormalTime(),
        type = type
    )
}