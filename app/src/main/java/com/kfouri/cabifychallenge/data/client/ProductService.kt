package com.kfouri.cabifychallenge.data.client

import com.kfouri.cabifychallenge.data.network.response.ProductsResponse
import retrofit2.Response
import javax.inject.Inject

class ProductService @Inject constructor(
    private val productClient: ProductClient
) {
    suspend fun getProducts(): Response<ProductsResponse> {
        return productClient.getProducts()
    }
}