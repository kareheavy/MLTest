package com.jhonjimenez.mercadolibretest.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jhonjimenez.mercadolibretest.local.model.ErrorApp

@Database(
    entities = [
        ErrorApp::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun errorAppDao(): ErrorAppDao
}