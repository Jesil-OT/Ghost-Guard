package com.jesil.ghostguard.home.presentation

sealed interface HomeActions {
    data class ToggleMotionDetection(val isToggled: Boolean) : HomeActions
    data class TogglePocketMode(val isToggled: Boolean) : HomeActions
}