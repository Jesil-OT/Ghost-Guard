package com.jesil.ghostguard.home.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.core.theme.primaryLight

@Composable
fun StatusIndicator(
    modifier: Modifier = Modifier,
    status: Boolean
) = Box(
    contentAlignment = Alignment.Center
) {
    if (status)
        PulseRingEffect(
            modifier = modifier,
            size = 200.dp
        )
    CircularRing(
        modifier = modifier, status = status, isShadowAvailable = false, circleRadius = 200.dp,
        shouldHaveBorder = true, layer = 1
    ) {
        CircularRing(
            status = status, circleRadius = 160.dp, isShadowAvailable = true,
            shouldHaveBorder = true, layer = 2
        ) {
            CircularRing(status = status, circleRadius = 120.dp, isShadowAvailable = true,
                shouldHaveBorder = true, layer = 3
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    content = {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = ImageVector.vectorResource(
                                if (status) R.drawable.ic_shield_filled else R.drawable.ic_shield_outline
                            ),
                            contentDescription = null,
                            tint = if (status) primary else Color.White
                        )
                        Text(
                            text = if (status) "PROTECTED" else "INACTIVE",
                            style = Typographys.bodyLarge.copy(
                                color = if (status) primary else Color.White,
                                fontSize = 14.sp
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun PulseRingEffect(
    modifier: Modifier = Modifier,
    size: Dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = .3f,
        targetValue = .10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )
    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .border(1.5.dp, primary.copy(alpha = alpha), CircleShape)
    )
}

@Composable
fun CircularRing(
    modifier: Modifier = Modifier,
    status: Boolean,
    circleRadius: Dp,
    layer: Int,
    isShadowAvailable: Boolean,
    shouldHaveBorder: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val targetColor = when (layer) {
        1 -> if (status) primary.copy(.4f) else Color.White
        2 -> if (status) primary.copy(.7f) else Color.White
        else -> if (status) primaryLight else Color.White
    }
    val indicatorColor = if (status) background else Color.White.copy(alpha = 0.10f)

    val shadowRadius = 9.dp
    Box(
        modifier = modifier
            .size(circleRadius + shadowRadius)
            .then(
                if (status && isShadowAvailable) Modifier.graphicsLayer(
                    shadowElevation = 30f,
                    shape = CircleShape,
                    ambientShadowColor = targetColor,
                    spotShadowColor = targetColor
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(circleRadius)
                .background(indicatorColor, shape = CircleShape)
                .then(
                    if (shouldHaveBorder) Modifier.border(
                        1.dp,
                        targetColor,
                        shape = CircleShape
                    )
                    else Modifier
                ),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
private fun StatusIndicatorPreview() {
    StatusIndicator(
        status = true,
    )
}