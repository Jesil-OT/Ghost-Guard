package com.jesil.ghostguard.warning

import android.app.Activity
import android.app.KeyguardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jesil.ghostguard.core.data.SecurityDataStore
import com.jesil.ghostguard.core.data.SecurityRepository
import com.jesil.ghostguard.core.data.SecurityState
import com.jesil.ghostguard.core.service.ServiceActions
import com.jesil.ghostguard.core.theme.background
import com.jesil.ghostguard.core.theme.secondary
import com.jesil.ghostguard.core.utils.BiometricsManager
import com.jesil.ghostguard.core.utils.ViewUtils.disableBackPress
import com.jesil.ghostguard.warning.presentation.WarningScreen
import com.jesil.ghostguard.warning.presentation.WarningViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "WarningActivity"

@AndroidEntryPoint
class WarningActivity : FragmentActivity() {
    @Inject lateinit var keyguardManager: KeyguardManager
    @Inject lateinit var securityDataStore: SecurityDataStore
    val biometricPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result here
            Toast.makeText(this, "Biometric permission granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            securityDataStore.setWarningActive(
                isActive = true
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        disableBackPress()

        keyguardManager.requestDismissKeyguard(this, null)

        setContent {
            val viewModel: WarningViewModel = hiltViewModel()

            val timerValue by viewModel.countDownTimerValue.collectAsStateWithLifecycle()
            val isTimeOver by viewModel.triggerAlert.collectAsStateWithLifecycle()

            Scaffold(
                content = { innerPadding ->
                    WarningScreen(
                        modifier = Modifier.background(
                            Brush.verticalGradient(
                                colors = listOf(secondary.copy(alpha = .3f), background),
                                startY = 0.0f,
                                endY = 1000.0f
                            )
                        ).padding(innerPadding),
                        countDownTimer = timerValue.toString(),
                        isTimerOver = isTimeOver,
                        onAuthenticate = {
                            BiometricsManager.authenticateWithBiometrics(
                                context = this,
                                onPermissionLaunched = { intent ->
                                    biometricPermissionLauncher.launch(intent)
                                },
                                onAuthenticationSuccess = {
                                    viewModel.cancelTimer()
                                    viewModel.launchSoundIntent(actions = ServiceActions.STOP_SOUND.toString())
                                    finish()
                                },
                                onAuthenticationError = { _, _ -> }
                            )
                        },
                    )
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            securityDataStore.setWarningActive(
                isActive = false
            )
        }
    }
}