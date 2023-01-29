package com.kfouri.cabifychallenge.domain.repository

import com.kfouri.cabifychallenge.data.model.Resource
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.model.ProductsModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Resource<ProductsModel>
    suspend fun addProducts(productModel: ProductModel)
    suspend fun buyProducts()
    suspend fun removeProductOrder(productModel: ProductModel)
    fun getCurrentOrder(): Flow<List<ProductModel>>
}