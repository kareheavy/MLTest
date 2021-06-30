package com.jhonjimenez.mercadolibretest.datasource.local.model


sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val data: T? = null ) : Resource<Nothing>()
}
