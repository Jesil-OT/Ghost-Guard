package com.jesil.ghostguard.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.home.data.MotionDetectionRepositoryImpl
import com.jesil.ghostguard.home.domain.MotionDetectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MotionDetectionModule {
    @Provides
    @Singleton
    fun provideMotionDetectionRepository(dataStore: DataStore<Preferences>): MotionDetectionRepository  = MotionDetectionRepositoryImpl(dataStore)
}