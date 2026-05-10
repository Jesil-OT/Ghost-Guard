package com.jesil.ghostguard.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
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
import com.jesil.ghostguard.R
import com.jesil.ghostguard.ui.theme.Typographys
import com.jesil.ghostguard.ui.theme.neutral
import com.jesil.ghostguard.ui.theme.primary

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isToggled: Boolean = false,
    icon: @Composable () -> Unit,
    onToggle: (Boolean) -> Unit
) {
    GlassmorphismCard(
        modifier = Modifier.fillMaxWidth(),
        isFlipped = isToggled,
        padding = 20.dp
    ){
        GlassmorphismCard(
            size = 360.dp,
            padding = 8.dp
        ) { icon() }
        Spacer(Modifier.width(15.dp))
        Column (
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = title,
                style = Typographys.bodyMedium.copy(
                    color = Color.White
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = Typographys.bodySmall.copy(
                    color = Color.White
                )
            )
        }
        Switch(
            modifier = Modifier.scale(.8f),
            checked = isToggled,
            onCheckedChange = onToggle,
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
                uncheckedBorderColor = Color.Transparent,
                uncheckedTrackColor = Color.White.copy(.4f),
                uncheckedThumbColor = Color.Black.copy(.5f),
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
        isToggled = true,
        onToggle = {}
    )
}