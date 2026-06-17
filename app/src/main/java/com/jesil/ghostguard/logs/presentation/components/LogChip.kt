package com.jesil.ghostguard.logs.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.logs.presentation.model.LogTypeUI
import com.jesil.ghostguard.logs.presentation.model.logModes

@Composable
fun LogChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelected: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit = {}
) {
    val selectedColor = if (isSelected) primary else Color.White.copy(alpha = .6f)
    Box(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = .15f),
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = 1.dp,
                color = selectedColor,
                shape = RoundedCornerShape(100.dp)
            )
            .then(
                if (isSelected) Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(100.dp),
                    ambientColor = primary.copy(.5f),
                    spotColor = primary
                ) else Modifier
            )
            .clip(RoundedCornerShape(100.dp))
            .clickable { onSelected() }
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun LogChips(
    modifier: Modifier = Modifier,
    logModes: List<LogTypeUI>,
    selectedMode: LogTypeUI,
    onSelected: (LogTypeUI) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            logModes.forEach { logMode ->
                val selectedColor = if (logMode == selectedMode) primary else Color.White.copy(alpha = .6f)
                LogChip(
                    isSelected = selectedMode == logMode,
                    onSelected = { onSelected(logMode) },
                    content = {
                        Text(
                            text = logMode.text,
                            style = Typographys.bodySmall.copy(
                                color = selectedColor
                            )
                        )
                    }
              )
            }
        }
    )
}

@Preview
@Composable
private fun LogChipPreview() {
    LogChip(
        isSelected = true,
        content = {
            Text(text = "All Events", color = primary)
        }
    )
}

@Preview
@Composable
private fun LogChipsPreview() {
    LogChips(
        logModes = logModes,
        selectedMode = logModes.first(),
        onSelected = {}
    )
}