package com.jhonjimenez.mercadolibretest.presentation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.ErrorResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Paging
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import com.jhonjimenez.mercadolibretest.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    private val _products = MutableLiveData<List<Results>>()
    val products: LiveData<List<Results>>
        get() = _products

    val message = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()
    val mainLoading = SingleLiveEvent<Boolean>()

    private var _queryBackup: String = ""
    private var _pagingBackup: Paging? = null
    private var _productsBackup: MutableList<Results> = arrayListOf()

    fun searchProduct(searchRequest: SearchRequest, isFromScroll: Boolean) = viewModelScope.launch {

        if (isFromScroll) {
            callSearchProduct(searchRequest, isFromScroll)
        } else {
            if (searchRequest.query != _queryBackup) {
                Timber.i("clean data query")
                _productsBackup.clear()
                _pagingBackup = null
            }

            callSearchProduct(searchRequest, isFromScroll)
        }
    }

    private suspend fun callSearchProduct(searchRequest: SearchRequest, isFromScroll: Boolean) {

        Timber.i("call api")

        if (isFromScroll) {
            isLoading.value = true
        } else {
            isLoading.value = false
            mainLoading.value = true
        }

        when (val result =
            withContext(Dispatchers.IO) { mainUseCase.searchProduct(searchRequest) }) {
            is Resource.Success -> {
                Timber.i("success call api")
                _queryBackup = searchRequest.query
                _pagingBackup = result.data.paging
                _productsBackup.addAll(result.data.results)

                _products.value = result.data.results
            }
            is Resource.Error<*> -> {
                Timber.i("error call api")
                val error = result.data as ErrorResponse

                _products.value = mutableListOf()
                message.value = error.message
            }
        }

        if (isFromScroll) {
            isLoading.value = false
        } else {
            mainLoading.value = false
        }
    }

    fun searchWithSameQuery() = viewModelScope.launch {
        _pagingBackup?.let {
            if (_productsBackup.size < it.total) {
                Timber.i("call api from scroll")
                val searchRequest = SearchRequest(
                    query = _queryBackup,
                    offset = it.offset.plus(it.limit),
                )
                searchProduct(searchRequest, isFromScroll = true)
            }
        }

    }

    fun getTotal(): Int = _pagingBackup?.total ?: 0
    fun getProducts(): List<Results> = _productsBackup


}
