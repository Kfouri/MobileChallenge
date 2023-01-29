package com.kfouri.cabifychallenge.data.network.response

data class ProductsResponse(
    val products: List<ProductResponse>
)

data class ProductResponse(
    val code: String,
    val name: String,
    val image: String,
    val price: Float
)