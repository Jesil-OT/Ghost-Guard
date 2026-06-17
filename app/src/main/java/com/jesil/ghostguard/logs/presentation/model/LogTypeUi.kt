package com.jesil.ghostguard.logs.presentation.model

enum class LogTypeUI(val text: String){
    ALL("All Events"),
    ALERTS("Alerts"),
    SYSTEM("System")
}

val logModes = listOf(LogTypeUI.ALL, LogTypeUI.ALERTS, LogTypeUI.SYSTEM)
