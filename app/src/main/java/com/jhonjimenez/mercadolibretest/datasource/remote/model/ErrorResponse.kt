package com.jhonjimenez.mercadolibretest.datasource.remote.model

data class ErrorResponse(
    var message: String = "",
    var error: String = "",
    var status: Int = 0
)