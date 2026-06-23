package com.jesil.ghostguard.settings.presntation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.home.presentation.components.GlassmorphismCard

@Composable
fun SettingsSwitchItem(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    switchValue: Boolean,
    useIcon: Boolean = false,
    iconScope : @Composable () -> Unit,
    onSwitchValueChanges: (Boolean) -> Unit
) {
    GlassmorphismCard(
        modifier = modifier,
        size = 10.dp,
        isFlipped = switchValue,
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    if (useIcon) {
                                       iconScope.invoke()
                                    }
                                    Text(
                                        text = title,
                                        style = Typographys.bodyMedium.copy(
                                            color = Color.White
                                        )
                                    )
                                }
                            )
                            Text(
                                text = subTitle,
                                style = Typographys.bodySmall.copy(
                                    color = Color.White,
                                )
                            )
                        }
                    )
                    Switch(
                        modifier = Modifier.scale(.7f),
                        checked = switchValue,
                        onCheckedChange = onSwitchValueChanges,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = background,
                            checkedTrackColor = primary,
                            uncheckedBorderColor = Color.White.copy(.5f),
                            uncheckedTrackColor = Color.White.copy(.4f),
                            uncheckedThumbColor = Color.White.copy(.5f),
                        )
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun SettingsSwitchItemPreview() {
    SettingsSwitchItem(
        modifier = Modifier.fillMaxWidth(),
        title = "Flashlight Strobing",
        subTitle = "Use device flash for visual deterrence",
        switchValue = true,
        iconScope = {
            Icon(
                modifier = Modifier
                    .scale(.9f)
                    .padding(end = 5.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_admin_panel),
                contentDescription = null,
                tint = primary
            )
        }
    ) { }
}