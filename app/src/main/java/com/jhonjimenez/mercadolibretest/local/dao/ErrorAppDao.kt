package com.jhonjimenez.mercadolibretest.local.dao

import androidx.room.*
import com.jhonjimenez.mercadolibretest.local.model.ErrorApp

@Dao
interface ErrorAppDao {

    @Insert
    fun insert(error: ErrorApp)

    @Update
    fun update(error: ErrorApp)

    @Delete
    fun delete(error: ErrorApp)

    @Delete
    fun deleteErrors(errors: List<ErrorApp>)

    @Query("SELECT * FROM error_app")
    fun getAll(): List<ErrorApp>
}