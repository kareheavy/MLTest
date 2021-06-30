package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Presentation (

	@SerializedName("display_currency") val displayCurrency : String
)