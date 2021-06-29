package com.jhonjimenez.mercadolibretest.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "error_app")
data class ErrorApp(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val error: String,
    val dateTime: String,
    var endPoint: String = ""
)