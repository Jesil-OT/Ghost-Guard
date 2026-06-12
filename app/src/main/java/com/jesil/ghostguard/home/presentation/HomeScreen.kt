package com.jesil.ghostguard.home.presentation

import android.Manifest
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesil.ghostguard.R
import com.jesil.ghostguard.home.presentation.components.FeatureCard
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary

const val TAG = "HomeScreen"
@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val isMotionDetectionArmed by viewModel.isMotionDetectionArmed.collectAsStateWithLifecycle()
    val pocketModeArmed by viewModel.isPocketModeEnabled.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "HomeScreen: Permission to show notification from 13+ is granted")
        }
    }

    LaunchedEffect( Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Log.d(TAG, "HomeScreen: Permission to show notification from 13+ is not needed")
        }
    }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    FeatureCard(
                        modifier = Modifier.padding(horizontal = 25.dp),
                        title = "Motion Detection",
                        description = if (isMotionDetectionArmed) "Armed" else "Detection inactive",
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.outline_sensors),
                                tint = primary,
                                contentDescription = null
                            )
                        },
                        isToggled = isMotionDetectionArmed,
                        onToggle = {
                            viewModel.onAction(HomeActions.ToggleMotionDetection(!isMotionDetectionArmed))
                        }
                    )
                    Spacer(Modifier.height(20.dp))
                    FeatureCard(
                        modifier = Modifier.padding(horizontal = 25.dp),
                        title = "Pocket Mode",
                        enabled = isMotionDetectionArmed,
                        description = if (pocketModeArmed) "active" else "inactive",
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.round_smartphone),
                                tint = animateColorAsState(
                                    targetValue = if (isMotionDetectionArmed) primary else Color.Black,
                                    label = "icon_enable_color"
                                ).value,
                                contentDescription = null
                            )
                        },
                        isToggled = pocketModeArmed,
                        onToggle = {
                            viewModel.onAction(HomeActions.TogglePocketMode(!pocketModeArmed))
                        }
                    )
                }
            )

}

