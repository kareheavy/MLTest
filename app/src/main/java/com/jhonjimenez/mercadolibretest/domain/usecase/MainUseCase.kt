package com.jhonjimenez.mercadolibretest.domain.usecase

import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Paging
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.repository.MainRepository

class MainUseCase(private val mainRepository: MainRepository) {
    suspend fun searchProduct(searchRequest: SearchRequest): Resource<BaseResponse> =
        mainRepository.searchProduct(searchRequest)
}