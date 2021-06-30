package com.jhonjimenez.mercadolibretest.datasource.remote.model

data class SearchRequest(
    val query: String,
    val offset: Int = 0,
    val limit: Int = 50
)