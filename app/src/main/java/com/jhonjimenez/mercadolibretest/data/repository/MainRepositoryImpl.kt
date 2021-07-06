package com.jhonjimenez.mercadolibretest.data.repository

import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.data.datasource.MainDataSource
import com.jhonjimenez.mercadolibretest.local.model.ErrorApp
import com.jhonjimenez.mercadolibretest.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.ErrorResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.repository.MainRepository
import com.jhonjimenez.mercadolibretest.presentation.utils.formatToServerDateTimeDefaults
import timber.log.Timber
import java.util.*

class MainRepositoryImpl(
    private val mainApiSource: MainApiSource,
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse> {
        val result = mainApiSource.searchProduct(searchRequest)

        if (result is Resource.Error<*>) {
            Timber.i("save error in log")
            val error = result.data as ErrorResponse
            mainDataSource.insertError(
                mapErrorResponseToErrorApp(
                    error.message,
                    error.error,
                    "search"
                )
            )
        }

        return result
    }

    private fun mapErrorResponseToErrorApp(
        message: String,
        error: String,
        endPoint: String
    ): ErrorApp {

        return ErrorApp(
            message = message,
            error = error,
            dateTime = Date().formatToServerDateTimeDefaults(),
            endPoint = endPoint
        )

    }
}