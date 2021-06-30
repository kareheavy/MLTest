package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.jhonjimenez.mercadolibretest.domain.repository.MainRepository
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import com.jhonjimenez.mercadolibretest.presentation.view.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideMainUseCase(mainRepository: MainRepository): MainUseCase = MainUseCase(mainRepository)
}