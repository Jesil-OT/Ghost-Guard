package com.jesil.ghostguard.warning.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.jesil.ghostguard.core.service.GhostGuardService
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
        isTimerDone()
    }

    fun startTimer(){
        timerRepository.startTimer()
    }

    fun cancelTimer(){
        timerRepository.cancelTimer()
    }

    fun isTimerDone(){
        if (timerRepository.isTimerFinished.value){
            Log.d(TAG, "isTimerDone: If not authenticated, play alert sound and notify authorities")
            Toast.makeText(application, "Play sound and triggerAlert value is ${triggerAlert.value} ", Toast.LENGTH_LONG).show()
//            triggerAlert.value = true
            launchSoundIntent(actions = ServiceActions.START_SOUND.toString())
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