package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import com.jhonjimenez.mercadolibretest.presentation.view.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun provideMainViewModel(mainUseCase: MainUseCase) = MainViewModel(mainUseCase)
}