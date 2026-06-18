package com.jesil.ghostguard.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.jesil.ghostguard.R

enum class Destination(
    val route: String,
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int?,
    val contentDescription: String
) {
    HOME("home", "Home", R.drawable.ic_shield_filled, R.drawable.ic_shield_outline,"Home"),
    SECURITY_LOGS("security_logs", "Security Logs", R.drawable.ic_log_history, null,"Security Logs"),
    SETTINGS("settings", "Settings", R.drawable.ic_settings_filled, R.drawable.outline_settings,"Settings")
}