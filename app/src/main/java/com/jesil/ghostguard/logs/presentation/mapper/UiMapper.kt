package com.jesil.ghostguard.logs.presentation.mapper

import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.LogTypeUI
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

fun LogTypeUI.toLogEventType(): List<LogEventType> {
    return when(this) {
        LogTypeUI.ALERTS -> listOf(
            LogEventType.ALARM_TRIGGERED,
            LogEventType.ALARM_DISARMED,
            LogEventType.WARNING_TRIGGERED
        )
        LogTypeUI.SYSTEM -> listOf(
            LogEventType.SYSTEM_ARMED,
            LogEventType.SYSTEM_DISARMED,
            LogEventType.POCKET_MODE_ARMED,
            LogEventType.POCKET_MODE_DISARMED
        )
        LogTypeUI.ALL -> LogEventType.entries
    }
}