package com.jhonjimenez.mercadolibretest.local

import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.local.model.ErrorApp

class MainLocalDataSourceImpl(private val errorAppDao: ErrorAppDao): MainDataSource {

    override suspend fun insertError(error: ErrorApp) {
        errorAppDao.insert(error)
    }

    override suspend fun getErrors() = errorAppDao.getAll()


}