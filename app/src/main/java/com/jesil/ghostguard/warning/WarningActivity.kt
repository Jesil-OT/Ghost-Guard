package com.jesil.ghostguard.warning

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesil.ghostguard.warning.presentation.WarningScreen
import com.jesil.ghostguard.warning.presentation.WarningViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "WarningActivity"

@AndroidEntryPoint
class WarningActivity: FragmentActivity() {
    @Inject
    lateinit var keyguardManager: KeyguardManager

    val biometricPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result here
            Toast.makeText(this, "Biometric permission granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        keyguardManager.requestDismissKeyguard(this, null)

        setContent {
            val viewModel: WarningViewModel = hiltViewModel()

            val timerValue by viewModel.countDownTimerValue.collectAsStateWithLifecycle()

            WarningScreen(
                countDownTimer = timerValue.toString(),
                onAuthenticate = {
                    authenticateWithBiometrics(
                        onAuthenticationSuccess = {
                            viewModel.cancelTimer()
                            finish()
                        },
                        onAuthenticationError = { _, _ ->
                            viewModel.cancelTimer()
                            viewModel.startTimer()
                        }
                    )
                },
            )
        }
    }

    private fun authenticateWithBiometrics(
        onAuthenticationSuccess: () -> Unit = {},
        onAuthenticationError: (errorCode: Int, errorMessage: String) -> Unit = { _, _ -> }
    ){
        val biometricManager = BiometricManager.from(this)
        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt = createBiometricPrompt(onAuthenticationSuccess, onAuthenticationError)
                biometricPrompt.authenticate(createPromptInfo())
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "Biometric hardware is missing", Toast.LENGTH_SHORT).show()
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Biometric is currently unavailable", Toast.LENGTH_SHORT)
                    .show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    this,
                    "Enrolling in biometrics that your app accepts",
                    Toast.LENGTH_SHORT,
                ).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val enrollIntent =
                        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                            )
                        }
                    biometricPermissionLauncher.launch(enrollIntent)
                }
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {}
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {}
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {}
        }
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Ghost Guard")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .setNegativeButtonText("Authenticate with key")
            .build()
    private fun createBiometricPrompt(
        onAuthenticationSuccess: () -> Unit = {},
        onAuthenticationError: (errorCode: Int, errorMessage: String) -> Unit = { _, _ -> }
    ): BiometricPrompt{
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e(TAG, "onAuthenticationError: Authentication error: $errorCode")
                onAuthenticationError(errorCode, errString.toString())
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e(TAG, "onAuthenticationError: Authentication error: Unknown authentication error")
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticationSuccess()
            }
        }

        val executor = ContextCompat.getMainExecutor(this)
        return BiometricPrompt(this, executor, callback)
    }
}