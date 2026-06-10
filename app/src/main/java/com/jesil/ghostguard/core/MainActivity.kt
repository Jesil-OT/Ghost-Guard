package com.jesil.ghostguard.core

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.navigation.Destination
import com.jesil.ghostguard.core.theme.GhostGuardTheme
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.primary
import com.jesil.ghostguard.home.presentation.HomeScreen
import com.jesil.ghostguard.logs.presentation.SecurityLogScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }

        enableEdgeToEdge()
        setContent {
            GhostGuardTheme {
                AppContent()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        val startDestination = Destination.HOME
        var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
        Scaffold(
            modifier = Modifier
                .background(background)
                .fillMaxSize(),
            topBar = {
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
                                .padding(end = 15.dp),
                            text = stringResource(R.string.ghost_guard),
                            style = Typographys.bodyLarge.copy(
                                color = primary,
                                textAlign = TextAlign.Center
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = background)
                )
            },
            content = { innerPadding ->
                AppNavHost(navController, startDestination, modifier = Modifier.padding(innerPadding))
            },
            bottomBar = {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, containerColor = background) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                navController.navigate(route = destination.route)
                                selectedDestination = index
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(destination.icon),
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = {
                                Text(
                                    destination.title,
                                    style = Typographys.bodyMedium.copy(
                                        color = primary
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = primary,
                                selectedTextColor = Color.White,
                                indicatorColor = primary.copy(alpha = 0.1f),
                                unselectedIconColor = Color.White.copy(alpha = 0.5f),
                                unselectedTextColor = Color.White.copy(alpha = 0.5f),
                            )
                        )
                    }
                }
            }
        )
    }

    @Composable
    fun AppNavHost(
        navController: NavHostController,
        startDestination: Destination,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController,
            startDestination = startDestination.route,
            modifier = modifier
        ) {
            Destination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        Destination.HOME -> HomeScreen()
                        Destination.SECURITY_LOGS -> SecurityLogScreen()
                        Destination.SETTINGS -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){Text(text = "Settings")}}
                    }
                }
            }
    }
}
