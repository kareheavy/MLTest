package com.jhonjimenez.mercadolibretest.datasource.remote

import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.datasource.local.model.ErrorApp
import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.ErrorResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.presentation.utils.formatToServerDateTimeDefaults
import timber.log.Timber
import java.util.*

class MainApiSourceImpl(
    private val api: MlApi,
    private val errorAppDao: ErrorAppDao
) : MainApiSource, BaseApiCall() {
    override suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse> =
        when (val response = safeApiCall(call = {
            api.search(
                searchRequest.query,
                searchRequest.offset,
                searchRequest.limit
            )
        })) {
            is Resource.Success -> response
            is Resource.Error<*> -> {

                var error: ErrorResponse
                try {
                    val errorCast = response.data as ErrorResponse
                    saveErrorInLog(errorCast.message, errorCast.error, "search")
                    error = errorCast
                    Resource.Error(error)
                } catch (e: Exception) {
                    saveErrorInLog(e.message.toString(), e.stackTraceToString(), "search")
                    error = ErrorResponse(
                        error = e.stackTraceToString(),
                        message = "Erro inesperado",
                    )
                    Resource.Error(error)
                }


            }
        }

    private fun saveErrorInLog(message: String, error: String, endPoint: String) {
        Timber.i("save error in log")
        errorAppDao.insert(
            ErrorApp(
                message = message,
                error = error,
                dateTime = Date().formatToServerDateTimeDefaults(),
                endPoint = endPoint
            )
        )
    }
}