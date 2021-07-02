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

class MainApiSourceImpl(private val api: MlApi) : MainApiSource, BaseApiCall() {
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

                try {
                    Resource.Error(response.data as ErrorResponse)
                } catch (e: Exception) {
                    val error = ErrorResponse(
                        error = e.stackTraceToString(),
                        message = "Erro inesperado",
                    )
                    Resource.Error(error)
                }
            }
        }


}