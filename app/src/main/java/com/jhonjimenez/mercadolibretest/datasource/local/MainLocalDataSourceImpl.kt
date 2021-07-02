package com.jhonjimenez.mercadolibretest.datasource.local

import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.datasource.local.model.ErrorApp

class MainLocalDataSourceImpl(private val errorAppDao: ErrorAppDao): MainDataSource {

    override suspend fun insertError(error: ErrorApp) {
        errorAppDao.insert(error)
    }

    override suspend fun getErrors() = errorAppDao.getAll()


}