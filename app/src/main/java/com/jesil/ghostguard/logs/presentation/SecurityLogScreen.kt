package com.jesil.ghostguard.logs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.logs.presentation.components.LogChips
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.model.GroupedLogModel
import com.jesil.ghostguard.logs.presentation.model.description
import com.jesil.ghostguard.logs.presentation.model.title

@Composable
fun SecurityLogScreen() {

}

@Composable
fun SecurityLogScreenInnerScreen(
    modifier: Modifier = Modifier,
    securityLogs: List<GroupedLogModel>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 20.dp, vertical = 25.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
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
            logModes = logModes,
            selectedMode = logModes.first(),
            onSelected = {}
        )
    }
}

@Preview
@Composable
private fun SecurityLogScreenPreview() {
    SecurityLogScreenInnerScreen(
        securityLogs = fakeHistory
    )
}

val logModes = listOf("All Events, Alerts, System")

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
    )
)
