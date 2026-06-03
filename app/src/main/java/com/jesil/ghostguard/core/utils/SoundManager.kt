package com.jesil.ghostguard.core.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import com.jesil.ghostguard.R
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val audioManager: AudioManager
) {
    private var mediaPlayer: MediaPlayer? = null

    fun startSound(){
        if (mediaPlayer?.isPlaying == true) return

        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
        mediaPlayer = MediaPlayer.create(
            context,
            R.raw.siren_alert_sound
        ).apply {
            isLooping = true
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            start()
        }
    }

    fun stopSound(){
        mediaPlayer?.let {
            try {
                if (it.isPlaying){
                    it.stop()
                }
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                it.release()
            }
        }
        mediaPlayer = null
    }
}