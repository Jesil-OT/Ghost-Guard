package com.jesil.ghostguard.logs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.mapper.toModel
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.utils.toDayString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SecurityLogViewModel @Inject constructor(
    securityLogRepo: SecurityLogRepository
) : ViewModel() {

    val logs: StateFlow<List<GroupedLogModel>> =
        securityLogRepo.getAllLogs()
            .map { securityLogs ->
                securityLogs.groupBy { it.timeStamp.toDayString() }
                    .map { (day, logs) ->
                        GroupedLogModel(
                            day = day,
                            securityLogs = logs.map { log -> log.toModel() }
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )
}



