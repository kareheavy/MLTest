package com.jhonjimenez.mercadolibretest.data.repository

import com.jhonjimenez.mercadolibretest.data.api.MainApiSource
import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.repository.MainRepository

class MainRepositoryImpl(private val mainApiSource: MainApiSource): MainRepository {
    override suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse> = mainApiSource.searchProduct(searchRequest)
}