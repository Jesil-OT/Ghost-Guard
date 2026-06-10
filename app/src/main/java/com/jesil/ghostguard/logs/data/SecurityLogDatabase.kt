package com.jesil.ghostguard.logs.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//import com.jesil.ghostguard.logs.data.model.Converters
import com.jesil.ghostguard.logs.data.model.SecurityLogEntity

@Database(entities = [SecurityLogEntity::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class SecurityLogDatabase: RoomDatabase() {
    abstract fun securityLogDao(): SecurityLogDao
}