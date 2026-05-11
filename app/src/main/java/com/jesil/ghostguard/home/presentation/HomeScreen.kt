package com.jesil.ghostguard.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesil.ghostguard.R
import com.jesil.ghostguard.home.presentation.components.FeatureCard
import com.jesil.ghostguard.ui.theme.Typographys
import com.jesil.ghostguard.ui.theme.background
import com.jesil.ghostguard.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    var motionToggle by remember { mutableStateOf(false) }
    var pocketModeToggle by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 15.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.outline_security),
                        contentDescription = stringResource(R.string.ghost_guard),
                        tint = primary
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        text = stringResource(R.string.ghost_guard),
                        style = Typographys.bodyLarge.copy(
                            color = primary,
                            textAlign = TextAlign.Center
                        )
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.outline_settings),
                        contentDescription = stringResource(R.string.settings),
                        tint = primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = background)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    FeatureCard(
                        modifier = Modifier.padding(horizontal = 25.dp),
                        title = "Motion Detection",
                        description = if (motionToggle) "Scanning 5m radius" else "Detection inactive",
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.outline_sensors),
                                tint = primary,
                                contentDescription = null
                            )
                        },
                        isToggled = motionToggle,
                        onToggle = {
                            motionToggle = !motionToggle
                        }
                    )
                    Spacer(Modifier.height(20.dp))
                    FeatureCard(
                        modifier = Modifier.padding(horizontal = 25.dp),
                        title = "Pocket Mode",
                        description = if (pocketModeToggle) "active" else "inactive",
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.round_smartphone),
                                tint = primary,
                                contentDescription = null
                            )
                        },
                        isToggled = pocketModeToggle,
                        onToggle = {
                            pocketModeToggle = !pocketModeToggle
                        }
                    )
                }
            )
        }
    )
}
