package com.jesil.ghostguard.logs.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jesil.ghostguard.core.theme.Typographys
import com.jesil.ghostguard.core.theme.background

@Composable
fun DeleteLogsDialog(
    onDismissRequest: () -> Unit,
    onDeleteConfirmation: () -> Unit,
    title: String,
    text: String,
    icon: ImageVector,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteConfirmation()
                }
            ) {
                Text(
                    text = "Delete",
                    style = Typographys.bodyMedium.copy(
                        color = Color(0xFFffb4ab)
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = "Dismiss", style = Typographys.bodyMedium.copy(
                        color = Color.White
                    )
                )
            }
        },
        title = {
            Text(
                text = title,
                style = Typographys.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
        }, text = {
            Text(
                text = text,
                style = Typographys.bodyMedium.copy(
                    color = Color.White
                )
            )
        }, icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        },
        containerColor = background
    )
}
