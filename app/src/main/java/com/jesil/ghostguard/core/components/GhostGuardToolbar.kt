package com.jesil.ghostguard.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.navigation.Destination
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GhostGuardToolbar(
    currentScreen: String? = null,
    onDeleteClick: () -> Unit = {}
) {
    val titleAdjustPadding = when (currentScreen) {
        Destination.SECURITY_LOGS.route -> 0.dp
        else -> 20.dp
    }
    TopAppBar(
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(30.dp),
                imageVector = ImageVector.vectorResource(R.drawable.outline_security),
                contentDescription = stringResource(R.string.ghost_guard),
                tint = primary
            )
        },
        actions = {
            when (currentScreen) {
                Destination.SECURITY_LOGS.route ->  Icon(
                    modifier = Modifier
                        .clickable{ onDeleteClick() }
                        .padding(end = 15.dp)
                        .size(30.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_all_logs),
                    tint = Color(0xFFffb4ab))
            }
        },
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = titleAdjustPadding),
                text = stringResource(R.string.ghost_guard),
                style = Typographys.bodyLarge.copy(
                    color = primary,
                    textAlign = TextAlign.Center
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = background)
    )
}
