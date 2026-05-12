package com.jesil.ghostguard.home.presentation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.jesil.ghostguard.core.service.GhostGuardService
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
): AndroidViewModel(application = application) {

    var toggleMotionDetectionState by mutableStateOf(false)

    fun onAction(actions: HomeActions) {
        when (actions) {
            is HomeActions.ToggleMotionDetection -> {
                // Toggle motion detection
                toggleService(actions.isToggled)
            }
            is HomeActions.TogglePocketMode -> {
                // Toggle pocket mode
            }
        }
    }

    private fun toggleService(shouldStart: Boolean) {
        val intent = Intent(application, GhostGuardService::class.java)
        if (shouldStart) {
            application.startForegroundService(intent)
        } else {
            application.stopService(intent)
        }
        toggleMotionDetectionState = shouldStart
    }
}