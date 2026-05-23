package com.jesil.ghostguard.core.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.jesil.ghostguard.warning.TAG

object BiometricsManager {

    fun authenticateWithBiometrics(
        context: Context,
        onPermissionLaunched: (Intent) -> Unit,
        onAuthenticationSuccess: () -> Unit = {},
        onAuthenticationError: (errorCode: Int, errorMessage: String) -> Unit = { _, _ -> }
    ) {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt =
                    createBiometricPrompt(context, onAuthenticationSuccess, onAuthenticationError)
                biometricPrompt.authenticate(createPromptInfo())
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(context, "Biometric hardware is missing", Toast.LENGTH_SHORT).show()
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(context, "Biometric is currently unavailable", Toast.LENGTH_SHORT)
                    .show()
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    context,
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
                    onPermissionLaunched(enrollIntent)
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
        context: Context,
        onAuthenticationSuccess: () -> Unit = {},
        onAuthenticationError: (errorCode: Int, errorMessage: String) -> Unit = { _, _ -> }
    ): BiometricPrompt {
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e(TAG, "onAuthenticationError: Authentication error: $errorCode")
                onAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e(
                    TAG,
                    "onAuthenticationError: Authentication error: Unknown authentication error"
                )
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticationSuccess()
            }
        }

        val executor = ContextCompat.getMainExecutor(context)
        return BiometricPrompt(context as FragmentActivity, executor, callback)
    }
}