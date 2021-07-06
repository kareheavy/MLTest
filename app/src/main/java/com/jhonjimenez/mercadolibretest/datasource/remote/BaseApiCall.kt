package com.jhonjimenez.mercadolibretest.datasource.remote

import com.jhonjimenez.mercadolibretest.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.ErrorResponse
import com.jhonjimenez.mercadolibretest.presentation.utils.Constants
import retrofit2.Response
import timber.log.Timber


open class BaseApiCall {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            Timber.i("error call api in safe api call")
            return Resource.Error(
                ErrorResponse(
                    message = Constants.error,
                    error = e.stackTraceToString()
                )
            )
        }

        if (!response.isSuccessful) {
            Timber.i("error https status code")
            return Resource.Error(
                ErrorResponse(
                    message = Constants.error,
                    error = response.code().toString()
                )
            )
        }

        return Resource.Success(response.body()!!)
    }

}