package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.local.MainLocalDataSourceImpl
import com.jhonjimenez.mercadolibretest.local.dao.ErrorAppDao
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun provideMainDataSource(errorAppDao: ErrorAppDao): MainDataSource = MainLocalDataSourceImpl(errorAppDao)
}