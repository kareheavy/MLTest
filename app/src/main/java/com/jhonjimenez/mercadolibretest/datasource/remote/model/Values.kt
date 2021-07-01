package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class Values(

	@SerializedName("id") val id: String,
	@SerializedName("name") val name: String,
	@SerializedName("struct") val struct: Struct,
	@SerializedName("source") val source: Long
)