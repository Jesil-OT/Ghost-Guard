package com.jesil.ghostguard.logs.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.logs.presentation.fakeHistory
import com.jesil.ghostguard.logs.presentation.model.LogEventType
import com.jesil.ghostguard.logs.presentation.model.SecurityLogModel
import com.jesil.ghostguard.logs.presentation.model.icon
import com.jesil.ghostguard.home.presentation.components.GlassmorphismCard

@Composable
fun LogItem(
    modifier: Modifier = Modifier,
    logModel: SecurityLogModel,
    isAlert: Boolean = logModel.type == LogEventType.ALARM_TRIGGERED || logModel.type == LogEventType.WARNING_TRIGGERED,
    isArmed: Boolean = logModel.type == LogEventType.SYSTEM_ARMED  || logModel.type == LogEventType.POCKET_MODE_ARMED
) {
    val textColor = if (isAlert) Color.Red.copy(alpha = .7f) else if (isArmed) primary else Color.White
    val timeTextColor = if (isAlert) Color.Red.copy(alpha = .5f) else Color.White
    GlassmorphismCard(
        modifier = modifier.fillMaxWidth(),
        isFlipped = isArmed,
        isError = isAlert,
        padding = 15.dp,
        size = 10.dp
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlassmorphismCard(
                size = 360.dp,
                padding = 5.dp,
                isFlipped = isArmed,
                isError = isAlert,
                content = {
                    Icon(
                        modifier = Modifier.scale(.7f),
                        imageVector = ImageVector.vectorResource( logModel.type.icon()),
                        contentDescription = null,
                        tint = textColor
                    )
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = logModel.title,
                    style = Typographys.bodySmall.copy(
                        color = textColor,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = logModel.timeStamp,
                    style = Typographys.bodySmall.copy(
                        color = timeTextColor,
                        fontSize = 11.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = logModel.description,
                style = Typographys.bodySmall.copy(
                    color = Color.White,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun LogItemPreview() {
    LogItem(
        logModel = fakeHistory.first().securityLogs[1]
    )
}