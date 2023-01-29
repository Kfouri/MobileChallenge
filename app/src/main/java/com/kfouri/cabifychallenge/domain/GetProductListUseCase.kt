package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.data.model.Resource
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.model.ProductsModel
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val getDiscountUseCase: GetDiscountUseCase
) {

    suspend operator fun invoke(): Resource<ProductsModel> {

        val resource = repository.getProducts()

        if (resource is Resource.Success) {
            val data = resource.data

            var productsModelList = ArrayList<ProductModel>()

            if (data != null) {
                productsModelList = data.products as ArrayList<ProductModel>
                productsModelList.forEach { item ->
                    item.discount = getDiscountUseCase.applyDiscount(item.code)
                }
            }

            resource.data = ProductsModel(productsModelList)

        }

        return resource
    }
}