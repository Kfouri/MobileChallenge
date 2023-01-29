package com.kfouri.cabifychallenge.data.network.mapper

import com.kfouri.cabifychallenge.data.network.response.ProductsResponse
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.model.ProductsModel

class ProductsMapper {

    fun toModel(productsResponse: ProductsResponse): ProductsModel {

        val modelList = ArrayList<ProductModel>()
        productsResponse.products.forEach { item ->
            val model = ProductModel(
                code = item.code,
                name = item.name,
                image = item.image,
                price = item.price,
                discount = null
            )
            modelList.add(model)
        }

        return ProductsModel(modelList)
    }
}