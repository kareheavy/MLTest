package com.jhonjimenez.mercadolibretest.presentation.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jhonjimenez.mercadolibretest.BuildConfig
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.datasource.local.model.ErrorApp
import com.jhonjimenez.mercadolibretest.datasource.remote.MLApi
import com.jhonjimenez.mercadolibretest.presentation.utils.formatToServerDateTimeDefaults
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(errorAppDao: ErrorAppDao): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addNetworkInterceptor(StethoInterceptor())
        clientBuilder
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder().build()
                val response = chain.proceed(newRequest)

                if(!response.isSuccessful){
                    response.body()?.let {
                        val source = it.source()
                        val charset = it.contentType()?.charset(Charset.forName("UTF-8"))
                        val buffer = source.buffer.readString(charset)
                        errorAppDao.insert(ErrorApp(0, response.message(), buffer, Date().formatToServerDateTimeDefaults(), response.request().url().toString()))
                    }
                }

                response
            }

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesMLApi(retrofit: Retrofit): MLApi = retrofit.create(MLApi::class.java)
}