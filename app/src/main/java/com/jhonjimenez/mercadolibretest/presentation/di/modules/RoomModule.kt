package com.jhonjimenez.mercadolibretest.presentation.di.modules

import android.app.Application

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jhonjimenez.mercadolibretest.datasource.local.dao.Database
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    private lateinit var roomDatabase: Database

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): Database {

        roomDatabase = Room.databaseBuilder(app.applicationContext,
            Database::class.java, "ml_test.db")
            .fallbackToDestructiveMigration()
            .build()

        return roomDatabase

    }

    @Provides
    @Singleton
    fun provideErrorAppDao(database: Database): ErrorAppDao = database.errorAppDao()
}