package com.jesil.ghostguard.settings.presntation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.settings.presntation.components.SectionHeader
import com.jesil.ghostguard.settings.presntation.components.SettingsDropdownItem
import com.jesil.ghostguard.settings.presntation.components.SettingsSliderItem
import com.jesil.ghostguard.settings.presntation.components.SettingsSwitchItem
import com.jesil.ghostguard.settings.presntation.components.dropDownAlarmTone

@Composable
fun SecuritySettingsScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
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

        item { SectionHeader(title = "Alarm Preferences") }

        item {
            SettingsSwitchItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Flashlight Strobing",
                subTitle = "Use device flash for visual deterrence.",
                switchValue = true,
                onSwitchValueChanges = {},
                iconScope = {}
            )
        }

        item {
            SettingsSwitchItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Max Volume Override",
                subTitle = "Bypass system volume limits for alert.",
                switchValue = false,
                iconScope = {},
                onSwitchValueChanges = {},

                )
        }

        item {
            SettingsDropdownItem(
                title = "Select Alarm Tone",
                dropdownItems = dropDownAlarmTone,
                onDropdownItemSelected = {},
                onDropdownClicked = {}
            )
        }

        item { SectionHeader(title = "Security Protocols") }

        item {
            SettingsSwitchItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Device Admin Status",
                subTitle = "Prevents unauthorized app uninstallation while armed",
                switchValue = false,
                useIcon = true,
                onSwitchValueChanges = {},
                iconScope = {
                    Icon(
                        modifier = Modifier
                            .scale(.9f)
                            .padding(end = 5.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_admin_panel),
                        contentDescription = null,
                        tint = if (false) primary else Color.White
                    )
                }
            )
        }


    }
}


@Preview
@Composable
private fun SecuritySettingsScreenPreview() {
    SecuritySettingsScreen()
}