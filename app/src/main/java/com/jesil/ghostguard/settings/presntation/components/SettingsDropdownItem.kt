package com.jesil.ghostguard.settings.presntation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.home.presentation.components.GlassmorphismCard

@Composable
fun SettingsDropdownItem(
    modifier: Modifier = Modifier,
    title: String,
    dropdownItems: List<String>,
    onDropdownClicked: () -> Unit,
    onDropdownItemSelected: (String) -> Unit
) {
    GlassmorphismCard(
        size = 10.dp,
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    Text(
                        text = title,
                        style = Typographys.bodyMedium.copy(
                            color = Color.White
                        )
                    )
                    DropdownItem(
                        modifier = Modifier.padding(top = 7.dp),
                        currentItem = dropdownItems.first(),
                        onItemClicked = onDropdownItemSelected
                    )
                }
            )
        }
    )
}

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    currentItem: String,
    onItemClicked: (String) -> Unit
) {
    GlassmorphismCard(
        size = 10.dp,
        modifier = modifier,
        content = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp),
                content = {
                    Text(
                        text = currentItem,
                        style = Typographys.bodyMedium.copy(
                            color = Color.White
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { onItemClicked(currentItem) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    )
}

val dropDownAlarmTone = listOf(
    "Tactical Siren (Default)",
    "Bird Song",
    "Big Hit",
    "Fire Alarm",
    "Power House"
)

@Preview
@Composable
private fun SettingsDropdownItemPreview() {
    SettingsDropdownItem(
        modifier = Modifier.fillMaxWidth(),
        title = "Select Alarm Tone",
        dropdownItems = dropDownAlarmTone,
        onDropdownItemSelected = {},
        onDropdownClicked = {}
    )
}