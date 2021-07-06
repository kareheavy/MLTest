package com.jhonjimenez.mercadolibretest.data.api

import com.jhonjimenez.mercadolibretest.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest

interface MainApiSource {
    suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse>
}