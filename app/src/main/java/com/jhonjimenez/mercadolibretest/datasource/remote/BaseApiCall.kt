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
            return Resource.Error(exception = e)
        }

        if (!response.isSuccessful) {
            if(response.code() == 304){
                return Resource.Error()
            }else{
                val responseErrorBody = response.errorBody()
                if (responseErrorBody != null) {
                    return Resource.Error(exception = IOException(responseErrorBody.string()))
                }
                return Resource.Error(exception = IOException("Error Occurred during getting safe Api result, Custom ERROR "))
            }
        }

        return Resource.Success(response.body()!!)
    }

}