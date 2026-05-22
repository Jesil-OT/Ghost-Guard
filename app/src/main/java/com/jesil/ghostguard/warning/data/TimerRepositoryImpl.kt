package com.jesil.ghostguard.warning.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jesil.ghostguard.warning.domain.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TimerRepositoryImpl : TimerRepository {
    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val countDownFlow: MutableStateFlow<Long> = MutableStateFlow(0L)

    private val isFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun startTimer()  {
        cancelTimer()
        isFinished.value = false
        timerJob = scope.launch {
            for (i in 10 downTo 0) {
                countDownFlow.value = i.toLong()
                delay(1000)
            }
            isFinished.value = true
        }
    }
    override fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun timerFinished(): Boolean = isFinished.value
}