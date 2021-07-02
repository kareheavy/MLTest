package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.data.repository.MainRepositoryImpl
import com.jhonjimenez.mercadolibretest.domain.repository.MainRepository
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideMainRepository(
        mainApiSource: MainApiSource,
        mainDataSource: MainDataSource
    ): MainRepository = MainRepositoryImpl(mainApiSource, mainDataSource)

}