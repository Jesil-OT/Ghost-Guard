package com.jesil.ghostguard.warning.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.core.data.SecurityDataStore
import com.jesil.ghostguard.core.data.SecurityRepository
import com.jesil.ghostguard.core.service.GhostGuardService
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.core.utils.Constants.ACTION_UPDATE_ACTIVITY_STATUS
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "WarningViewModel"
@HiltViewModel
class WarningViewModel @Inject constructor(
    private val application: Application,
    private val timerRepository: TimerRepository,
    private val logRepository: SecurityLogRepository
): AndroidViewModel(application) {
    var countDownTimerValue = timerRepository.countDownFlow
    var triggerAlert = timerRepository.isTimerFinished

    init {
        startTimer()
        observeTimerCompletion()
    }

    private fun observeTimerCompletion(){
        viewModelScope.launch {
            timerRepository.isTimerFinished.collect{ timerDone ->
                if (timerDone){
                    Log.d(TAG, "isTimerDone: If not authenticated, play alert sound and notify authorities")
                    launchSoundIntent(actions = ServiceActions.START_SOUND.toString())
                    logRepository.addLog(
                        SecurityLog(
                            title = LogEventType.ALARM_TRIGGERED.title(),
                            description = LogEventType.ALARM_TRIGGERED.description(),
                            timeStamp = System.currentTimeMillis(),
                            type = LogEventType.ALARM_TRIGGERED,
                        )
                    )
                }
            }
        }
    }

    fun startTimer(){
        timerRepository.startTimer()
    }

    fun cancelTimer(){
        timerRepository.cancelTimer()
        // disable log event
        viewModelScope.launch {
            logRepository.addLog(
                SecurityLog(
                    title = LogEventType.ALARM_DISARMED.title(),
                    description = LogEventType.ALARM_DISARMED.description(),
                    timeStamp = System.currentTimeMillis(),
                    type = LogEventType.ALARM_DISARMED,
                )
            )
        }
    }

    fun launchSoundIntent(actions: String){
        application.startService(
            Intent(application, GhostGuardService::class.java).apply {
                action = actions
            }
        )
    }
}