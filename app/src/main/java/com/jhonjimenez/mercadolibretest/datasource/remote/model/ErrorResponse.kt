package com.jhonjimenez.mercadolibretest.datasource.remote.model

data class ErrorResponse(
    val message: String,
    val error: String,
    val status: Int
)