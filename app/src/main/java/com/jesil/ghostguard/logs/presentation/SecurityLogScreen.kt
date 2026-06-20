package com.jesil.ghostguard.logs.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.logs.presentation.components.DeleteLogsDialog
import com.jesil.ghostguard.logs.presentation.components.LogChips
import com.jesil.ghostguard.logs.presentation.components.LogItem
import com.jesil.ghostguard.logs.presentation.components.LogItemHeader
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.LogTypeUI
import com.jesil.ghostguard.logs.presentation.model.SecurityLogAction
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.logModes
import com.jesil.ghostguard.logs.presentation.model.title

@Composable
fun SecurityLogScreen(
    deleteDialogState: Boolean,
    onDismissCLick: () -> Unit,
) {
    val viewModel: SecurityLogViewModel = hiltViewModel()
    val securityLogs by viewModel.logs.collectAsStateWithLifecycle()
    val logChipState by viewModel.logChipState.collectAsStateWithLifecycle()

    SecurityLogScreenInnerScreen(
        securityLogs = securityLogs,
        logModes = logModes,
        selectedMode = logChipState,
        onLogSelected = { viewModel.onAction(SecurityLogAction.OnLogChipSelected(it)) }
    )

    if (deleteDialogState) {
        DeleteLogsDialog(
            title =  "Delete all logs",
            text = "Are you sure you want to delete all logs?",
            icon = ImageVector.vectorResource(R.drawable.baseline_warning),
            onDismissRequest = onDismissCLick,
            onDeleteConfirmation = {
                viewModel.onAction(SecurityLogAction.OnLogDeleted(null))
                onDismissCLick()
            }
        )
    }
}

@Composable
fun SecurityLogScreenInnerScreen(
    modifier: Modifier = Modifier,
    securityLogs: List<GroupedLogModel>,
    logModes: List<LogTypeUI>,
    selectedMode: LogTypeUI,
    onLogSelected: (LogTypeUI) -> Unit,
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
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize(),
            targetState = securityLogs.isEmpty(),
            label = "Empty State"
        ) { state ->
            when (state) {
                true -> Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .background(
                                Color.Black.copy(alpha = .15f),
                                shape = RoundedCornerShape(100.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = primary,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(horizontal = 15.dp, vertical = 5.dp),
                        text = "No logs found!",
                        style = Typographys.bodyMedium.copy(
                            color = primary,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    content = {
                        securityLogs.forEach { groupedLogModel ->
                            item { LogItemHeader(groupedLogModel.day) }
                            items(groupedLogModel.securityLogs) { securityLog ->
                                LogItem(logModel = securityLog)
                            }
                        }
                    }
                )
            }

        }

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
