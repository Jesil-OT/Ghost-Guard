package com.jesil.ghostguard.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jesil.ghostguard.home.data.PocketModeRepositoryImpl
import com.jesil.ghostguard.home.domain.PocketModeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PocketModeModule {
    @Provides
    @Singleton
    fun providePocketModeRepository(dataStore: DataStore<Preferences>): PocketModeRepository = PocketModeRepositoryImpl(dataStore)

}