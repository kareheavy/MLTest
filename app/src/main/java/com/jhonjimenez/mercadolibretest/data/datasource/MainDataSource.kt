package com.jhonjimenez.mercadolibretest.data.datasource

import com.jhonjimenez.mercadolibretest.local.model.ErrorApp

interface MainDataSource {
    suspend fun insertError(error: ErrorApp)
    suspend fun  getErrors() : List<ErrorApp>
}