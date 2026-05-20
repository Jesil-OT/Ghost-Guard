package com.jesil.ghostguard.core.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs
import kotlin.math.sqrt

class SensorMonitor(
    private val onMotionDetected: () -> Unit,
): SensorEventListener {
    // --- Tuning Configuration Constants ---
    private val thresholdSlop = 1.8f       // Delta deviation from gravity (m/s^2)
    private val sustainedWindowMs = 350L    // Continuous time movement must endure

    // --- State Tracking Variables ---
    private var firstSpikeTimestamp = 0L

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        // 1. Calculate absolute acceleration magnitude
        val magnitude = sqrt(x * x + y * y + z * z)

        // 2. Compute absolute deviation from normal Earth gravity
        val detlaAcceleration = abs(magnitude - SensorManager.GRAVITY_EARTH)

        // 3. Evaluation Window Logic Pipeline
        if (detlaAcceleration > thresholdSlop) {
            val currentTime = System.currentTimeMillis()

            if (firstSpikeTimestamp == 0L) {
                // Potential movement sequence initiated. Start the clock.
                firstSpikeTimestamp = currentTime
            }else{
                // Movement is ongoing. Check if it has crossed the validation duration.
                val elapsedMovementTime = currentTime - firstSpikeTimestamp
                if (elapsedMovementTime >= sustainedWindowMs) {
                    onMotionDetected()
                    resetMonitor() // Reset state to prevent infinite fire loops
                }
            }
        } else {
            // The force dropped back to resting baseline.
            // It was just an instantaneous vibration/bump! Discard it.
            resetMonitor()
        }
    }

    fun resetMonitor() {
        firstSpikeTimestamp = 0L
    }

}