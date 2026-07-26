package com.jesil.ghostguard.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.settings.data.FlashlightStrobingImpl
import com.jesil.ghostguard.settings.data.SettingsRepositoryImpl
import com.jesil.ghostguard.settings.domain.FlashlightStrobing
import com.jesil.ghostguard.settings.domain.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository = SettingsRepositoryImpl(
        dataStore = dataStore
    )

    @Provides
    @Singleton
    fun provideFlashlightStrobing(
        @ApplicationContext context: Context,
        settingsRepository: SettingsRepository): FlashlightStrobing =
        FlashlightStrobingImpl(
            settingsRepository = settingsRepository,
            context = context
        )
}