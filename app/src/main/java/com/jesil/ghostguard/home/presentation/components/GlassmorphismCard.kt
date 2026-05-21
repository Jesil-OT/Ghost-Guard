package com.jesil.ghostguard.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.core.theme.primary

@Composable
fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    size: Dp = 25.dp,
    padding: Dp = 24.dp,
    isFlipped: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val glassBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.25f),
            Color.White.copy(alpha = 0.15f)
        )
    )

    val glassBrush2 = Brush.linearGradient(
        colors = listOf(
            primary.copy(alpha = 0.20f),
            primary.copy(alpha = 0.1f)
        )
    )

    val background = if (isFlipped) glassBrush2 else glassBrush

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size))
            .background(background)
            .border(
                width = 1.dp,
                brush = background,
                shape = RoundedCornerShape(size)
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(size),
                ambientColor = Color.Transparent,
                spotColor = Color.Transparent
            ),
        content = {
            Row(
                modifier = Modifier.padding(padding),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    )

}