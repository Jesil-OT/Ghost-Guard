package com.jesil.ghostguard.home.presentation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.core.service.GhostGuardService
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.home.domain.MotionDetectionRepository
import com.jesil.ghostguard.home.domain.PocketModeRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val motionDetectionRepository: MotionDetectionRepository,
    private val pocketModeRepository: PocketModeRepository
): AndroidViewModel(application = application) {
    val isMotionDetectionArmed = motionDetectionRepository.isArmed.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    val isPocketModeEnabled = pocketModeRepository.isPocketModeEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    fun onAction(actions: HomeActions) {
        when (actions) {
            is HomeActions.ToggleMotionDetection -> {
                // Toggle motion detection
                viewModelScope.launch {
                    motionDetectionRepository.setArmed(actions.isToggled)
                    toggleService(actions.isToggled)
                }
            }
            is HomeActions.TogglePocketMode -> {
                // Toggle pocket mode
                viewModelScope.launch {
                    pocketModeRepository.setPocketModeEnabled(actions.isToggled)
                }
            }
        }
    }

    private fun toggleService(shouldStart: Boolean) {
        val intent = Intent(application, GhostGuardService::class.java)
        if (shouldStart){
            application.startService(intent.also { it.action = ServiceActions.START_MOTION_DETECTION.toString() })
        } else {
            application.startService(intent.also { it.action = ServiceActions.STOP.toString() })
        }
    }
}