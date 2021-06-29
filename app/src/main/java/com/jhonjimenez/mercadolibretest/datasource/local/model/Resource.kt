package com.jhonjimenez.mercadolibretest.datasource.local.model


sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val errors: HashMap<String, String>? = null,
        val exception: Exception? = null
    ) : Resource<Nothing>()
}
