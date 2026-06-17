package com.jesil.ghostguard.logs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.mapper.toLogEventType
import com.jesil.ghostguard.logs.presentation.mapper.toModel
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.LogTypeUI
import com.jesil.ghostguard.logs.presentation.model.SecurityLogAction
import com.jesil.ghostguard.logs.presentation.model.logModes
import com.jesil.ghostguard.logs.presentation.utils.toDayString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SecurityLogViewModel @Inject constructor(
    securityLogRepo: SecurityLogRepository
) : ViewModel() {

    private val _logChipState = MutableStateFlow(logModes.first())
    val logChipState: StateFlow<LogTypeUI> = _logChipState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val logs: StateFlow<List<GroupedLogModel>> = _logChipState
        .flatMapLatest { mode ->
            val type = mode.toLogEventType()
            securityLogRepo.getLogsByType(type)
                .map { securityLogs ->
                    securityLogs.groupBy { it.timeStamp.toDayString() }
                    .map { (day, logs) ->
                        GroupedLogModel(
                            day = day,
                            securityLogs = logs.map { log -> log.toModel() }
                        )
                    }
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun onAction(action: SecurityLogAction){
        when(action){
            is SecurityLogAction.OnLogChipSelected -> {
                _logChipState.value = action.currentChip
            }
        }
    }
}



