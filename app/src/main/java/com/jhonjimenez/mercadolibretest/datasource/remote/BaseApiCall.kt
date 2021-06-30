package com.jhonjimenez.mercadolibretest.datasource.remote

import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


open class BaseApiCall {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            return Resource.Error(e)
        }

        if (!response.isSuccessful) {
            return Resource.Error(response.body()!!)
        }

        return Resource.Success(response.body()!!)
    }

}