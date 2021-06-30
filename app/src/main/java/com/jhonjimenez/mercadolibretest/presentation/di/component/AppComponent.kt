package com.jhonjimenez.mercadolibretest.presentation.di.component

import android.app.Application
import com.jhonjimenez.mercadolibretest.presentation.MLApplication
import com.jhonjimenez.mercadolibretest.presentation.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MLApplicationModule::class,
        FragmentModule::class,
        ActivityModule::class,
        RoomModule::class,
        ViewModelModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        ApiSourceModule::class,
        NetModule::class,
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MLApplication)
}