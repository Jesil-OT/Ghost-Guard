package com.jesil.ghostguard.settings.presntation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.primary
import java.util.Locale

@Composable
fun SectionHeader(title: String) =
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        content = {
            Text(
                title.uppercase(Locale.ROOT),
                style = Typographys.bodyMedium.copy(color = primary, fontSize = 14.sp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp),
                color = Color.White.copy(alpha = .2f),
                thickness = 1.dp
            )
        }
    )