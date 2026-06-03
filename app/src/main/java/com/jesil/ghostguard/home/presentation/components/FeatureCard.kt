package com.jesil.ghostguard.home.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.neutral
import com.jesil.ghostguard.core.theme.primary

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    enabled: Boolean = true,
    isToggled: Boolean = false,
    icon: @Composable () -> Unit,
    onToggle: (Boolean) -> Unit
) {
    val enabledColor = animateColorAsState(
        targetValue = if (enabled) Color.White else Color.Black.copy(.8f),
        label = "icon_enable_color"
    )
    GlassmorphismCard(
        modifier = modifier.fillMaxWidth().clickable(
            onClick = { if (enabled) onToggle(!isToggled) }
        ),
        isFlipped = isToggled,
        enabled = enabled,
        padding = 15.dp
    ) {
        val modifier = if (isToggled) {
            Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(360.dp),
                    ambientColor = primary.copy(.5f),
                    spotColor = primary
                )
                .background(Color.Transparent)
        } else {
            Modifier
        }
        Box(
            modifier = modifier,
        ) {
            GlassmorphismCard(
                size = 360.dp,
                padding = 8.dp,
                isFlipped = isToggled,
                enabled = enabled
            ) { icon() }
        }
        Spacer(Modifier.width(15.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = Typographys.bodyMedium.copy(
                    color = enabledColor.value
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = Typographys.bodySmall.copy(
                    color = enabledColor.value
                )
            )
        }
        Switch(
            modifier = Modifier.scale(.7f),
            checked = isToggled,
            onCheckedChange = onToggle,
            enabled = enabled,
            thumbContent = {
                if (isToggled)
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White
                    )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = neutral,
                checkedTrackColor = primary,
                uncheckedBorderColor = Color.White.copy(.5f),
                uncheckedTrackColor = Color.White.copy(.4f),
                uncheckedThumbColor = Color.White.copy(.5f),
                disabledUncheckedThumbColor = Color.Black.copy(.5f),
                disabledUncheckedTrackColor = Color.Black.copy(.3f),
                disabledUncheckedBorderColor = Color.Black,
                disabledCheckedThumbColor = Color.Black.copy(.5f),
                disabledCheckedTrackColor = Color.Black.copy(.3f),
                disabledCheckedBorderColor = Color.Black
            )
        )
    }
}

@Preview
@Composable
private fun FeatureCardPrev() {

    FeatureCard(
        title = "Motion Detection",
        description = "Detection inactive",
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.outline_sensors),
                tint = primary,
                contentDescription = null
            )
        },
        isToggled = false,
        onToggle = {}
    )
}