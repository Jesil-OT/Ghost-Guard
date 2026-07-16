package com.jesil.ghostguard.warning.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.secondary
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    maxSeconds: Long,
    currentSeconds: Long,
    size: Dp = 200.dp,
    trackColor: Color = Color.Black.copy(alpha = .4f),
    strokeWidth: Dp = 12.dp,
    updateMaxSeconds: (Long) -> Unit
) {
//    var maxSeconds by remember { mutableLongStateOf(10L) }
    LaunchedEffect(currentSeconds) {
        if (currentSeconds > maxSeconds) {
//            maxSeconds = currentSeconds
            updateMaxSeconds(currentSeconds)
        }
    }

    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val targetProgress = if (maxSeconds > 0) currentSeconds.toFloat() / maxSeconds else 0f

    val progress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "progressCircle"
    )

    val timerProgressColor = if (currentSeconds <= 3L) Color(0xFFffb4ab) else secondary
    val progressColor  by animateColorAsState(
        targetValue = timerProgressColor,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "progressColor"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val topLeftOffset = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val arcSize = Size(
                width = this.size.width - strokeWidthPx,
                height = this.size.height - strokeWidthPx
            )
            drawCircle(
                color = trackColor,
                radius = (this.size.width - strokeWidthPx) / 2,
                style = Stroke(width = strokeWidthPx)
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeftOffset,
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
            content = {
                val textColor by animateColorAsState(
                    targetValue = if (currentSeconds <= 3L) Color(0xFFffb4ab) else Color.White,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
                    label = "textColor"
                )
                Text(
                    text = currentSeconds.toString(),
                    style = Typographys.bodyLarge.copy(
                        color = textColor,
                        fontSize = 100.sp,
                    )
                )
                Text(
                    modifier = Modifier.offset(
                        x = 0.dp,
                        y = (-15).dp
                    ),
                    text = "Seconds".uppercase(),
                    style = Typographys.bodySmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp
                    )
                )
            }
        )
    }
}

@Preview
@Composable
private fun CountDownTimerPreview() {
    CountDownTimer(
        modifier = Modifier,
        currentSeconds = 4L,
        maxSeconds = 10L,
        updateMaxSeconds = {}
    )
}