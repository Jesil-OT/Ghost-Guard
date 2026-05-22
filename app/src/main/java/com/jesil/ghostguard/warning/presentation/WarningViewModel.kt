package com.jesil.ghostguard.warning.presentation

import android.app.Application
import android.hardware.biometrics.BiometricPrompt
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarningViewModel @Inject constructor(
    private val application: Application,
    private val timerRepository: TimerRepository
): AndroidViewModel(application) {
    var countDownTimerValue = timerRepository.countDownFlow
    var shouldTriggerAlarm = MutableStateFlow(false)
        private set

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
        if (timerRepository.timerFinished()){
            Toast.makeText(application, "If not authenticated, play alert sound and notify authorities", Toast.LENGTH_SHORT).show()
            shouldTriggerAlarm.value = true
        }
    }
}