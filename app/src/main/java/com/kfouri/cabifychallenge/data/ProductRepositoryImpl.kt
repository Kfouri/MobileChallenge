package com.kfouri.cabifychallenge.data

import com.kfouri.cabifychallenge.data.client.ProductService
import com.kfouri.cabifychallenge.data.database.ProductDao
import com.kfouri.cabifychallenge.data.database.model.ProductEntity
import com.kfouri.cabifychallenge.data.model.Resource
import com.kfouri.cabifychallenge.data.network.mapper.ProductsMapper
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.model.ProductsModel
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductService,
    private val productDao: ProductDao
): ProductRepository {

    override suspend fun getProducts(): Resource<ProductsModel> {

        val response = api.getProducts()

        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(ProductsMapper().toModel(result))
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun addProducts(productModel: ProductModel) {
        productDao.addProduct(
            ProductEntity(
                code = productModel.code,
                name = productModel.name,
                amount = productModel.amount,
                price = productModel.price,
                total = 0f
                )
        )
    }

    override suspend fun buyProducts() {
        productDao.buyProducts()
    }

    override suspend fun removeProductOrder(productModel: ProductModel) {
        productDao.removeProductOrder(productModel.code)
    }

    override fun getCurrentOrder(): Flow<List<ProductModel>> {
        return productDao.getProducts().map { items ->
            items.map {
                ProductModel(
                    it.id,
                    it.code,
                    it.name,
                    "",
                    it.price,
                    null,
                    it.amount,
                    0f
                )
            }
        }
    }

}