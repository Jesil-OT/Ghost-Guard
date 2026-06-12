package com.jesil.ghostguard.logs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.ghostguard.logs.domain.SecurityLog
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
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
    private val securityLogRepo: SecurityLogRepository
) : ViewModel() {

    val logs: StateFlow<List<GroupedLogModel>> =
        securityLogRepo.getAllLogs()
            .map { securityLogs ->
                securityLogs.groupBy { it.timeStamp }
                    .map { (day, logs) ->
                        GroupedLogModel(
                            day = day.toDayString(),
                            securityLogs = logs.map { log -> log.toModel() }
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )

    private fun SecurityLog.toModel(): SecurityLogModel {
        return SecurityLogModel(
            id = id,
            title = title,
            description = description,
            timeStamp = timeStamp.toNormalTime(),
            type = type
        )
    }

    private fun Long.toNormalTime(): String {
        val date = Date(this)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }
}

fun Long.toDayString(): String {
    // assume your timestamp string is in a known format, e.g. "yyyy-MM-dd HH:mm:ss"
    val date = Date(this)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }

    calendar.time = date

    return when {
        isSameDay(calendar, today) -> "Today"
        isSameDay(calendar, yesterday) -> "Yesterday"
        else -> {
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date)
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
