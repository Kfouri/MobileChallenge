package com.kfouri.cabifychallenge.data

import com.kfouri.cabifychallenge.data.model.Resource
import com.kfouri.cabifychallenge.domain.model.Discount
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.model.ProductsModel
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : ProductRepository {

    private val listProducts = ArrayList<ProductModel>()

    private val currentOrderModel = ArrayList<ProductModel>()

    override suspend fun getProducts(): Resource<ProductsModel> {

        listProducts.add(
            ProductModel(0L, "CODE", name = "NAME", image = "", 0f, Discount("", 0f), 0, 0f)
        )
        return Resource.Success(
            ProductsModel(
                listProducts
            )
        )
    }

    override suspend fun addProducts(productModel: ProductModel) {
        currentOrderModel.add(productModel)
    }

    override suspend fun buyProducts() {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductOrder(productModel: ProductModel) {
        currentOrderModel.remove(productModel)
    }

    override fun getCurrentOrder(): Flow<List<ProductModel>> {
        return flow {
            emit(currentOrderModel)
        }
    }
}