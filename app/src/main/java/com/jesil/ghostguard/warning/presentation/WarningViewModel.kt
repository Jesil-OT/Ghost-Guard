package com.jesil.ghostguard.warning.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.core.service.GhostGuardService
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.settings.domain.SettingsRepository
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "WarningViewModel"

@HiltViewModel
class WarningViewModel @Inject constructor(
    private val application: Application,
    private val timerRepository: TimerRepository,
    private val logRepository: SecurityLogRepository,
    private val settingsRepository: SettingsRepository
) : AndroidViewModel(application) {
    val countDownTimerValue = timerRepository.countDownFlow
    val triggerAlert = timerRepository.isTimerFinished

    init {
        startTimer()
        observeTimerCompletion()
    }

    private fun observeTimerCompletion() {
        viewModelScope.launch {
            timerRepository.isTimerFinished.collect { timerDone ->
                if (timerDone) {
                    Log.d(TAG, "observeTimerCompletion: Timer finished, triggering alarm.")
                    launchSoundIntent(ServiceActions.START_SOUND)
                }
            }
        }
    }

    fun startTimer() {
        viewModelScope.launch {
            val settings = settingsRepository.getSecurityValues().first()
            val timer = settings.proximityDelaySec.toInt()
            timerRepository.startTimer(timer)
        }
    }

    fun cancelTimer() {
        timerRepository.cancelTimer()
        viewModelScope.launch {
            logRepository.addLog(LogEventType.ALARM_DISARMED)
        }
    }

    fun launchSoundIntent(action: ServiceActions) {
        application.startService(
            Intent(application, GhostGuardService::class.java).apply {
                this.action = action.name
            }
        )
    }
}