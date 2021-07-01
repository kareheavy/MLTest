package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.jhonjimenez.mercadolibretest.presentation.view.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

}