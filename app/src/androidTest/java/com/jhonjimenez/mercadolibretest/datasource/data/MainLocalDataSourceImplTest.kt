package com.jhonjimenez.mercadolibretest.datasource.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.local.MainLocalDataSourceImpl
import com.jhonjimenez.mercadolibretest.local.dao.Database
import com.jhonjimenez.mercadolibretest.local.model.ErrorApp
import com.jhonjimenez.mercadolibretest.presentation.utils.formatToServerDateTimeDefaults
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*


internal class MainLocalDataSourceImplTest {

    private lateinit var db: Database
    private lateinit var mainLocalDataSource: MainDataSource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val errorAppDao = db.errorAppDao()

        mainLocalDataSource = MainLocalDataSourceImpl(errorAppDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()

    }

    @Test
    fun insertError() = runBlocking {
        val error = ErrorApp(
            message = "Test error",
            error = "Error Exception",
            dateTime = Date().formatToServerDateTimeDefaults(),
            endPoint = "test"
        )

        // save error in local db
        mainLocalDataSource.insertError(error)

        //get error all insert
        val errors = mainLocalDataSource.getErrors()

        //check errors in db
        assertEquals(1, errors.size)

        assertTrue(errors.find { x -> x.id == 1 } != null)
    }
}