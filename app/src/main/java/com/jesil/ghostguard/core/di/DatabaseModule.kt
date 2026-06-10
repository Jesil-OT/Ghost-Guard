package com.jesil.ghostguard.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jesil.ghostguard.logs.data.SecurityLogDao
import com.jesil.ghostguard.logs.data.SecurityLogDatabase
import com.jesil.ghostguard.logs.data.SecurityLogRepositoryImpl
import com.jesil.ghostguard.logs.domain.SecurityLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SecurityLogDatabase =
        Room.databaseBuilder(
            context,
            SecurityLogDatabase::class.java,
            "security_log_database"
        ).build()


    @Singleton
    @Provides
    fun provideLogDao(database: SecurityLogDatabase) = database.securityLogDao()

    @Provides
    @Singleton
    fun provideSecurityLogRepository(dao: SecurityLogDao): SecurityLogRepository =
        SecurityLogRepositoryImpl(dao)
}