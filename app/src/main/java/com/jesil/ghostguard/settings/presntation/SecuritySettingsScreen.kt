package com.jesil.ghostguard.settings.presntation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.settings.presntation.components.SectionHeader
import com.jesil.ghostguard.settings.presntation.components.SettingsSliderItem
import java.util.Locale

@Composable
fun SecuritySettingsScreen() {

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(background),
        contentPadding = PaddingValues(25.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("System Configuration", style = Typographys.bodyLarge.copy(color = Color.White))
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                "Fine-tune detection parameters and security responses.",
                style = Typographys.bodySmall.copy(color = Color.White, fontSize = 14.sp)
            )
        }

        item { SectionHeader(title = "Detection Sensitivity") }

        item {
            SettingsSliderItem(
                title = "Motion Threshold",
                subTitle = "Adjust the sensitivity of motion detection.",
                sliderValue = 0.8f,
                sliderValueLabel = "High",
                valueRange = 0f..1f,
                onSliderValueChange = {}
            )
        }

        item {
            SettingsSliderItem(
                title = "Proximity Delay",
                subTitle = "Time before triggering alarm upon detection.",
                sliderValue = 0.3f,
                sliderValueLabel = "3s",
                valueRange = 0f..1f,
                onSliderValueChange = {}
            )
        }

    }
}


@Preview
@Composable
private fun SecuritySettingsScreenPreview() {
    SecuritySettingsScreen()
}