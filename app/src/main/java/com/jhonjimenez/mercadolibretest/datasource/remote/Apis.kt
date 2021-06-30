package com.jhonjimenez.mercadolibretest.datasource.remote

import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MlApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseResponse>
}