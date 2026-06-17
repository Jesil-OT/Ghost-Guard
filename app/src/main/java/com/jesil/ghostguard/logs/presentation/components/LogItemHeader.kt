package com.jesil.ghostguard.logs.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.primary

@Composable
fun LogItemHeader(day: String) {
    Text(
        modifier = Modifier
            .background(
                Color.Black.copy(alpha = .15f),
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = 1.dp,
                color = if (day == "Today") primary else Color.White.copy(
                    .5f
                ),
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 10.dp, vertical = 2.dp),
        text = day,
        style = Typographys.bodySmall.copy(
            color = if (day == "Today") primary else Color.White.copy(
                .5f
            )
        )
    )
}