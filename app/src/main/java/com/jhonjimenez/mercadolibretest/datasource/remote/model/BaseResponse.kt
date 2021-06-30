package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("site_id") val siteId: String,
    @SerializedName("query") val query: String,
    @SerializedName("paging") val paging: Paging,
    @SerializedName("results") val results: List<Results>
)