package com.jhonjimenez.mercadolibretest.presentation.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jhonjimenez.mercadolibretest.datasource.remote.model.BaseResponse
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.domain.usecase.MainUseCase
import com.jhonjimenez.mercadolibretest.local.model.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var useCase: MainUseCase

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = MainViewModel(useCase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when fetching result ok check liveData`() {

        val request: SearchRequest = mockk(relaxed = true)
        val baseResponse: BaseResponse = mockk(relaxed = true)
        val expected = Resource.Success(baseResponse)

        coEvery { useCase.searchProduct(any()) } returns expected

        runBlocking {
            viewModel.callSearchProduct(request, false)
        }

        assert(viewModel.products.value != null)
        assertEquals(expected.data.paging, viewModel._pagingBackup)
        assertEquals(expected.data.results, viewModel.products.value)
        assertEquals(expected.data.query, viewModel._queryBackup)

        coVerify {
            useCase.searchProduct(any())
            viewModel.callSearchProduct(request, false)
        }

    }

}