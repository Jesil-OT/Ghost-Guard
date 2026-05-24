package com.jesil.ghostguard.warning.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.GhostGuardTheme
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.core.theme.secondary
import com.jesil.ghostguard.core.theme.tertiary
import com.jesil.ghostguard.warning.presentation.components.AlertBox
import com.jesil.ghostguard.warning.presentation.components.CountDownTimer
import java.util.Locale

@Composable
fun WarningScreen(
    modifier: Modifier = Modifier,
    countDownTimer: String,
    isTimerOver: Boolean = false,
    onAuthenticate: () -> Unit,
) {
    Box{
        if (isTimerOver){
            AlertBox(modifier = Modifier.fillMaxSize())
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize(),
            content = {
                Row(
                    modifier = Modifier.padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Warning Senty Alert",
                        tint = secondary
                    )
                    Text(
                        text = "Sentry Alert".uppercase(Locale.ROOT),
                        style = Typographys.bodyMedium.copy(
                            color = secondary
                        )
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    text = "Unauthorized Access Detected".uppercase(Locale.ROOT),
                    style = Typographys.bodyLarge.copy(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        letterSpacing = 4.sp,
                        lineHeight = 35.sp
                    )
                )
                CountDownTimer(
                    modifier = Modifier.padding(vertical = 15.dp)
                        .weight(1f),
                    time = countDownTimer
                )
                Text(
                    text = "Identify yourself or alarm will sound and authorities will be notified.",
                    style = Typographys.bodyMedium.copy(
                        color = Color.White.copy(.5f),
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(Modifier.height(30.dp))
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    onClick = onAuthenticate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondary,
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.outline_fingerprint),
                        contentDescription = null,
                        tint = Color.Black.copy(alpha = .7f)
                    )
                    Text(
                        text = "Authenticate",
                        style = Typographys.bodyMedium.copy(
                            color = Color.Black.copy(alpha = .7f),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(Modifier.height(80.dp))
            }
        )
    }
}

@Preview
@Composable
private fun WarningScreenPreview() {
    WarningScreen(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(secondary.copy(alpha = .3f), background),
                    startY = 0.0f,
                    endY = 1000.0f
                )
            ),
        countDownTimer = "9",
        onAuthenticate = {},
    )
}