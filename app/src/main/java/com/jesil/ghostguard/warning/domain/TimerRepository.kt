package com.jesil.ghostguard.warning.domain

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow

interface TimerRepository {
    val countDownFlow: MutableStateFlow<Long>
    fun startTimer()
    fun cancelTimer()
    fun timerFinished(): Boolean
}