package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Country(

	@SerializedName("id") val id: String,
	@SerializedName("name") val name: String
)