package com.jhonjimenez.mercadolibretest.datasource.remote

import com.google.gson.Gson
import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.datasource.local.dao.ErrorAppDao
import com.jhonjimenez.mercadolibretest.datasource.local.model.ErrorApp
import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.ErrorResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.presentation.utils.formatToServerDateTimeDefaults
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
                val error = mapBaseResponseToErrorResponse(response.data as String)
                errorAppDao.insert(
                    ErrorApp(
                        message = error.message,
                        error = error.error,
                        dateTime = Date().formatToServerDateTimeDefaults(),
                        endPoint = "search"
                    )
                )

                Resource.Error(error)
            }
        }

    private fun mapBaseResponseToResultResponse(response: String): Resource<BaseResponse> {
        val gson = Gson()
        val jsonString: String = gson.toJson(response)
        val baseResponse = gson.fromJson(jsonString, BaseResponse::class.java)

        return Resource.Success(baseResponse)
    }

    private fun mapBaseResponseToErrorResponse(response: String): ErrorResponse {
        val gson = Gson()
        val jsonString: String = gson.toJson(response)

        return gson.fromJson(jsonString, ErrorResponse::class.java)
    }
}