package com.jesil.ghostguard.settings.presntation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.home.presentation.components.GlassmorphismCard

@Composable
fun SettingsSliderItem(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    sliderValue: Float,
    sliderValueLabel: String,
    valueRange: ClosedFloatingPointRange<Float>,
    onSliderValueChange: (Float) -> Unit
) {
    GlassmorphismCard(
        size = 10.dp,
        modifier = modifier,
        content = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).weight(1f),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            content = {
                                Text(
                                    text = title,
                                    style = Typographys.bodyMedium.copy(
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = subTitle,
                                    style = Typographys.bodySmall.copy(
                                        color = Color.White
                                    )
                                )
                            }
                        )
                            Text(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .background(
                                    primary.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(5.dp)
                                ).padding(4.dp),
                                text = sliderValueLabel,
                                style = Typographys.bodyMedium.copy(
                                    color = primary,
                                    fontSize = 14.sp
                                )
                            )
                    }
                )
                Slider(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 5.dp),
                    value = sliderValue,
                    onValueChange = onSliderValueChange,
                    valueRange = valueRange,
                    colors = SliderDefaults.colors(
                        thumbColor = primary,
                        activeTrackColor = primary.copy(alpha = .4f),
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
            }
        }
    )
}

@Preview
@Composable
private fun SettingsSliderItemPreview() {
    SettingsSliderItem(
        modifier = Modifier,
        title = "Motion Threshold",
        subTitle = "Adjust the sensitivity of motion detection.",
        sliderValue = 0.8f,
        sliderValueLabel = "High",
        valueRange = 0f..1f,
        onSliderValueChange = {}
    )
}