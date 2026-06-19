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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jesil.ghostguard.R
import com.jesil.ghostguard.core.components.GhostGuardToolbar
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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Scaffold(
            modifier = Modifier
                .background(background)
                .fillMaxSize(),
            topBar = { GhostGuardToolbar() },
            content = { innerPadding ->
                AppNavHost(navController, Destination.HOME, modifier = Modifier.padding(innerPadding))
            },
            bottomBar = {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, containerColor = background) {
                    Destination.entries.forEach { destination ->
                        val isSelected = currentRoute == destination.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(route = destination.route){
                                    popUpTo(Destination.HOME.route){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        if (isSelected) destination.selectedIcon else destination.unselectedIcon
                                            ?: destination.selectedIcon
                                    ),
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = {
                                Text(
                                    destination.title,
                                    style = Typographys.bodyMedium.copy(
                                        fontSize = 14.sp
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = primary,
                                selectedTextColor = primary,
                                indicatorColor = primary.copy(alpha = 0.1f),
                                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                unselectedTextColor = Color.White.copy(alpha = 0.7f),
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
                        Destination.SETTINGS -> Box(modifier = Modifier
                            .fillMaxSize()
                            .background(background), contentAlignment = Alignment.Center){Text(text = "Settings")}}
                    }
                }
            }
    }
}