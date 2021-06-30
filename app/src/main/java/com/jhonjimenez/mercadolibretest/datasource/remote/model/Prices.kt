package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Prices (

	@SerializedName("id") val id : String,
	@SerializedName("prices") val prices : List<Prices>,
	@SerializedName("presentation") val presentation : Presentation,
	@SerializedName("payment_method_prices") val paymentMethodPrices : List<String>,
	@SerializedName("reference_prices") val referencePrices : List<String>,
	@SerializedName("purchase_discounts") val purchaseDiscounts : List<String>
)