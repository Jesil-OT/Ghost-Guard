package com.jesil.ghostguard.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
    padding: Dp = 10.dp,
    isFlipped: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    content: @Composable BoxScope.() -> Unit
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

    val glassBrush3 = Brush.linearGradient(
        colors = listOf(
            Color.DarkGray.copy(alpha = 0.15f),
            Color.DarkGray.copy(alpha = 0.05f)
        )
    )

    val glassBrush4 = Brush.linearGradient(
        colors = listOf(
            Color.Red.copy(alpha = 0.25f),
            Color.Red.copy(alpha = 0.15f)
        )
    )

    val background =
        if (isFlipped) glassBrush2 else if (!enabled) glassBrush3 else if (isError) glassBrush4 else glassBrush

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
            Box(
                modifier = Modifier.padding(padding),
                content = content
            )
        }
    )

}