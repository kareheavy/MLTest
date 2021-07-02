package com.jhonjimenez.mercadolibretest.presentation.view.main

import com.jhonjimenez.mercadolibretest.datasource.local.model.Resource
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Paging
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var mockedSearchRequest: SearchRequest
    private lateinit var baseResponse: BaseResponse
    private lateinit var mockedUseCase: MainUseCase
    private lateinit var mockedPaging: Paging

    @Before
    fun setup() {
        mockedSearchRequest = mockkClass(SearchRequest::class)
        baseResponse = mockkClass(BaseResponse::class)
        mockedPaging = mockkClass(Paging::class)
        mockedUseCase = mockkClass(MainUseCase::class)
    }

    @Test
    fun successResponse() = runBlocking {

        every { mockedSearchRequest.query } returns "test"
        every { baseResponse.paging } returns mockedPaging

        every {
            runBlocking { mockedUseCase.searchProduct(mockedSearchRequest) }
        } returns Resource.Success(baseResponse)

        val results = mockkClass(Results::class)

        every { baseResponse.results } returns listOf(results)

        viewModel = MainViewModel(mockedUseCase)

        viewModel.searchProduct(mockedSearchRequest, false)

        val products = viewModel.products.value

        assertEquals(1, products!!.size)
    }


}