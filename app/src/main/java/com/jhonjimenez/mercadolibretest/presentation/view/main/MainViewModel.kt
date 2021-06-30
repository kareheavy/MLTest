package com.jhonjimenez.mercadolibretest.presentation.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel(private val mainUseCase: MainUseCase) : ViewModel() {
    fun searchProduct(searchRequest: SearchRequest) = viewModelScope.launch{
        val result = withContext(Dispatchers.IO){ mainUseCase.searchProduct(searchRequest)}

        when(result){
            is Resource.Success ->{ Timber.i(result.data.toString())}
            is Resource.Error<*> -> {Timber.i(result.data.toString())}
        }
    }
}