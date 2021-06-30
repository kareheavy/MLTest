package com.jhonjimenez.mercadolibretest.domain.repository

import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest

interface MainRepository {
    suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse>
}