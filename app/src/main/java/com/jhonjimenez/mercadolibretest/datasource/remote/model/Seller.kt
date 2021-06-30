package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Seller(

	@SerializedName("id") val id: Int,
	@SerializedName("permalink") val permalink: String,
	@SerializedName("registration_date") val registrationDate: String,
	@SerializedName("car_dealer") val carDealer: Boolean,
	@SerializedName("real_estate_agency") val realEstateAgency: Boolean,
	@SerializedName("tags") val tags: List<String>?
)