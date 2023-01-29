package com.kfouri.cabifychallenge.domain.model

data class ProductsModel(
    val products: List<ProductModel>
)

data class ProductModel(
    val id: Long = 0,
    val code: String,
    val name: String,
    val image: String,
    var price: Float,
    var discount: Discount?,
    var amount: Int = 1,
    var total: Float = 0f
)

data class Discount(
    val type: String,
    val price: Float,
    val discount: String
)

data class PriceDiscount(
    val price: Float,
    val total: Float
)