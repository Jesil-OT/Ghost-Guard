package com.jesil.ghostguard.logs.presentation.model

import com.jesil.ghostguard.R

fun LogEventType.title() = when (this) {
    LogEventType.ALARM_DISARMED -> "Alarm Disarmed"
    LogEventType.ALARM_TRIGGERED -> "Alarm Triggered"
    LogEventType.POCKET_MODE_ARMED -> "Pocket Mode Armed"
    LogEventType.POCKET_MODE_DISARMED -> "Pocket Mode Disarmed"
    LogEventType.SYSTEM_ARMED -> "System Armed"
    LogEventType.SYSTEM_DISARMED -> "System Disarmed"
    LogEventType.WARNING_TRIGGERED -> "Warning Triggered"
}

fun LogEventType.description() = when (this) {
    LogEventType.ALARM_DISARMED -> "Protection deactivated via biometric authentication."
    LogEventType.ALARM_TRIGGERED -> "Unauthorized access detected! The security alarm has been triggered due to suspicious activity."
    LogEventType.POCKET_MODE_ARMED -> "Pocket Mode activated by user, sensors engaged."
    LogEventType.POCKET_MODE_DISARMED -> "You've turned off Pocket mode, sensors disable"
    LogEventType.SYSTEM_ARMED -> "Ghost Guard Motion detection activated by user, sensors engaged."
    LogEventType.SYSTEM_DISARMED -> "You've turned off Motion detection, sensors disable"
    LogEventType.WARNING_TRIGGERED -> "Sudden movement detected while system was armed. High sensitivity threshold crossed."
}

fun LogEventType.icon() = when (this) {
    LogEventType.ALARM_DISARMED -> R.drawable.outline_sensors
    LogEventType.ALARM_TRIGGERED -> R.drawable.outline_detector_alarm
    LogEventType.POCKET_MODE_ARMED -> R.drawable.round_smartphone
    LogEventType.POCKET_MODE_DISARMED -> R.drawable.round_smartphone
    LogEventType.SYSTEM_ARMED -> R.drawable.outline_security
    LogEventType.SYSTEM_DISARMED -> R.drawable.outline_security
    LogEventType.WARNING_TRIGGERED -> R.drawable.baseline_warning
}