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
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title
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
    private val pocketModeRepository: PocketModeRepository,
    private val securityLogRepository: SecurityLogRepository,
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
                    when (actions.isToggled) {
                        true -> securityLogRepository.addLog(
                            SecurityLog(
                                title = LogEventType.SYSTEM_ARMED.title(),
                                description = LogEventType.SYSTEM_ARMED.description(),
                                timeStamp = System.currentTimeMillis(),
                                type = LogEventType.SYSTEM_ARMED,
                            )
                        )
                        false -> securityLogRepository.addLog(
                            SecurityLog(
                                title = LogEventType.SYSTEM_DISARMED.title(),
                                description = LogEventType.SYSTEM_DISARMED.description(),
                                timeStamp = System.currentTimeMillis(),
                                type = LogEventType.SYSTEM_DISARMED,
                            )
                        )
                    }
                }
            }
            is HomeActions.TogglePocketMode -> {
                // Toggle pocket mode
                viewModelScope.launch {
                    pocketModeRepository.setPocketModeEnabled(actions.isToggled)
                    when (actions.isToggled) {
                        true -> securityLogRepository.addLog(
                            SecurityLog(
                                title = LogEventType.POCKET_MODE_ARMED.title(),
                                description = LogEventType.POCKET_MODE_ARMED.description(),
                                timeStamp = System.currentTimeMillis(),
                                type = LogEventType.POCKET_MODE_ARMED,
                            )
                        )
                        false -> securityLogRepository.addLog(
                            SecurityLog(
                                title = LogEventType.POCKET_MODE_DISARMED.title(),
                                description = LogEventType.POCKET_MODE_DISARMED.description(),
                                timeStamp = System.currentTimeMillis(),
                                type = LogEventType.POCKET_MODE_DISARMED,
                            )
                        )
                    }
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