package com.jesil.ghostguard.logs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable 
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.logs.presentation.components.LogChips
import com.jesil.ghostguard.logs.presentation.components.LogItem
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title

@Composable
fun SecurityLogScreen() {
    val viewModel: SecurityLogViewModel = hiltViewModel()
    val securityLogs by viewModel.logs.collectAsStateWithLifecycle()
    
    var selectedMode by remember { mutableStateOf(logModes.first()) }

//    val filteredLogs = remember(securityLogs, selectedMode) {
//        when (selectedMode) {
//            "Alerts" -> {
//                securityLogs.map { groupedLog ->
//                    groupedLog.copy(
//                        securityLogs = groupedLog.securityLogs.filter { log ->
//                            log.type == LogEventType.ALARM_TRIGGERED ||
//                                    log.type == LogEventType.WARNING_TRIGGERED ||
//                                    log.type == LogEventType.ALARM_DISARMED
//                        }
//                    )
//                }.filter { it.securityLogs.isNotEmpty() }
//            }
//
//            "System" -> {
//                securityLogs.map { groupedLog ->
//                    groupedLog.copy(
//                        securityLogs = groupedLog.securityLogs.filter { log ->
//                            log.type == LogEventType.SYSTEM_ARMED ||
//                                    log.type == LogEventType.SYSTEM_DISARMED ||
//                                    log.type == LogEventType.POCKET_MODE_ARMED ||
//                                    log.type == LogEventType.POCKET_MODE_DISARMED
//                        }
//                    )
//                }.filter { it.securityLogs.isNotEmpty() }
//            }
//
//            else -> securityLogs
//        }
//    }

    SecurityLogScreenInnerScreen(
        securityLogs = securityLogs,
        logModes = logModes,
        selectedMode = selectedMode,
        onLogSelected = { currentLog -> selectedMode = currentLog }
    )
}

@Composable
fun SecurityLogScreenInnerScreen(
    modifier: Modifier = Modifier,
    securityLogs: List<GroupedLogModel>,
    logModes: List<String>,
    selectedMode: String,
    onLogSelected: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp, top = 25.dp),
            text = "Security Log",
            style = Typographys.bodyLarge.copy(
                color = Color.White
            )
        )
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Review recent sensor activity and system events.",
            style = Typographys.bodySmall.copy(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        LogChips(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 20.dp),
            logModes = logModes,
            selectedMode = selectedMode,
            onSelected = onLogSelected
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            content = {
                securityLogs.forEach { groupedLogModel ->
                    item {
                        Text(
                            modifier = Modifier
                                .background(
                                    Color.Black.copy(alpha = .15f),
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (groupedLogModel.day == "Today") primary else Color.White.copy(
                                        .5f
                                    ),
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                            text = groupedLogModel.day,
                            style = Typographys.bodySmall.copy(
                                color = if (groupedLogModel.day == "Today") primary else Color.White.copy(
                                    .5f
                                )
                            )
                        )
                    }
                    items(groupedLogModel.securityLogs) { securityLog ->
                        LogItem(logModel = securityLog)
                    }
                }
            }
        )
    }
}

@Preview(device = "spec:width=360dp,height=788dp")
@Composable
private fun SecurityLogScreenPreview() {
    SecurityLogScreenInnerScreen(
        securityLogs = fakeHistory,
        logModes = logModes,
        selectedMode = logModes.first(),
        onLogSelected = {}
    )
}

val logModes = listOf("All Events", "Alerts", "System")

val fakeHistory = listOf(
    GroupedLogModel(
        day = "Today",
        securityLogs = listOf(
            SecurityLogModel(
                id = 0,
                title = LogEventType.SYSTEM_ARMED.title(),
                description = LogEventType.SYSTEM_ARMED.description(),
                timeStamp = "10:00 AM",
                type = LogEventType.SYSTEM_ARMED
            ),
            SecurityLogModel(
                id = 1,
                title = LogEventType.ALARM_TRIGGERED.title(),
                description = LogEventType.ALARM_TRIGGERED.description(),
                timeStamp = "10:30 AM",
                type = LogEventType.ALARM_TRIGGERED
            ),
            SecurityLogModel(
                id = 2,
                title = LogEventType.ALARM_DISARMED.title(),
                description = LogEventType.ALARM_DISARMED.description(),
                timeStamp = "11:00 AM",
                type = LogEventType.ALARM_DISARMED
            )
        )
    ),
    GroupedLogModel(
        day = "Yesterday",
        securityLogs = listOf(
            SecurityLogModel(
                id = 3,
                title = LogEventType.WARNING_TRIGGERED.title(),
                description = LogEventType.WARNING_TRIGGERED.description(),
                timeStamp = "10:00 AM",
                type = LogEventType.WARNING_TRIGGERED
            ),
            SecurityLogModel(
                id = 4,
                title = LogEventType.POCKET_MODE_ARMED.title(),
                description = LogEventType.POCKET_MODE_ARMED.description(),
                timeStamp = "10:30 AM",
                type = LogEventType.POCKET_MODE_ARMED
            )
        )
    )
)
