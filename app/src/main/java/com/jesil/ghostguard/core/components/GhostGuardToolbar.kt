package com.jesil.ghostguard.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GhostGuardToolbar() {
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
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
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
