package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Attributes(

    @SerializedName("name") val name: String,
    @SerializedName("value_name") val valueName: String,
    @SerializedName("id") val id: String,
    @SerializedName("values") val values: List<Values>,
    @SerializedName("attribute_group_id") val attributeGroupId: String,
    @SerializedName("attribute_group_name") val attributeGroupName: String,
    @SerializedName("source") val source: Long,
    @SerializedName("value_id") val valueId: String
)