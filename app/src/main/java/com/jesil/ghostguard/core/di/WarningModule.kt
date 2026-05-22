package com.jesil.ghostguard.core.di

import com.jesil.ghostguard.warning.data.TimerRepositoryImpl
import com.jesil.ghostguard.warning.domain.TimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WarningModule {

    @Provides
    @Singleton
    fun provideTimerRepository(): TimerRepository = TimerRepositoryImpl()
}