package com.jesil.ghostguard.warning.presentation.components

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys

@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    time: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        content = {
            Text(
                text = time,
                style = Typographys.bodyLarge.copy(
                    color = Color.White,
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