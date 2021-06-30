package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class SellerAddress(

	@SerializedName("id") val id: String,
	@SerializedName("comment") val comment: String,
	@SerializedName("address_line") val address_line: String,
	@SerializedName("zip_code") val zip_code: String,
	@SerializedName("country") val country: Country,
	@SerializedName("state") val state: State,
	@SerializedName("city") val city: City,
	@SerializedName("latitude") val latitude: String,
	@SerializedName("longitude") val longitude: String
)