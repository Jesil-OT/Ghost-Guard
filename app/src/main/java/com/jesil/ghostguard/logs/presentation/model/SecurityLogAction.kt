package com.jesil.ghostguard.logs.presentation.model

sealed interface SecurityLogAction {
    data class OnLogChipSelected(val currentChip: LogTypeUI): SecurityLogAction
}