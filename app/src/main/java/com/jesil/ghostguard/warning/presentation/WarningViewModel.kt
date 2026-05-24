package com.jesil.ghostguard.warning.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.core.service.GhostGuardService
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "WarningViewModel"
@HiltViewModel
class WarningViewModel @Inject constructor(
    private val application: Application,
    private val timerRepository: TimerRepository
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
                }
            }
        }
    }

    fun startTimer(){
        timerRepository.startTimer()
    }

    fun cancelTimer(){
        timerRepository.cancelTimer()
    }

    fun launchSoundIntent(actions: String){
        application.startService(
            Intent(application, GhostGuardService::class.java).apply {
                action = actions
            }
        )
    }
}