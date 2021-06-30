package com.jhonjimenez.mercadolibretest.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class Results(

	@SerializedName("id") val id: String,
	@SerializedName("site_id") val siteId: String,
	@SerializedName("title") val title: String,
	@SerializedName("seller") val seller: Seller,
	@SerializedName("price") val price: Int,
	@SerializedName("prices") val prices: Prices,
	@SerializedName("sale_price") val salePrice: String,
	@SerializedName("currency_id") val currencyId: String,
	@SerializedName("available_quantity") val availableQuantity: Int,
	@SerializedName("sold_quantity") val soldQuantity: Int,
	@SerializedName("buying_mode") val buyingMode: String,
	@SerializedName("listing_type_id") val listingTypeId: String,
	@SerializedName("stop_time") val stopTime: String,
	@SerializedName("condition") val condition: String,
	@SerializedName("permalink") val permalink: String,
	@SerializedName("thumbnail") val thumbnail: String,
	@SerializedName("thumbnail_id") val thumbnailId: String,
	@SerializedName("accepts_mercadopago") val acceptsMercadopago: Boolean,
	@SerializedName("installments") val installments: Installments,
	@SerializedName("address") val address: Address,
	@SerializedName("shipping") val shipping: Shipping,
	@SerializedName("seller_address") val sellerAddress: SellerAddress,
	@SerializedName("attributes") val attributes: List<Attributes>,
	@SerializedName("original_price") val originalPrice: String,
	@SerializedName("category_id") val categoryId: String,
	@SerializedName("official_store_id") val officialStoreId: String,
	@SerializedName("domain_id") val domainId: String,
	@SerializedName("catalog_product_id") val catalogProductId: String,
	@SerializedName("tags") val tags: List<String>,
	@SerializedName("order_backend") val orderBackend: Int,
	@SerializedName("use_thumbnail_id") val useThumbnailId: Boolean
)