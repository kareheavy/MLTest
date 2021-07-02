package com.jhonjimenez.mercadolibretest.presentation.di.modules


import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.datasource.remote.MlApi


import com.jhonjimenez.mercadolibretest.datasource.remote.MainApiSourceImpl
import dagger.Module
import dagger.Provides

@Module
class ApiSourceModule {

    @Provides
    fun provideMainApiSource(api: MlApi): MainApiSource = MainApiSourceImpl(api)
}