package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import javax.inject.Inject

class AddProductOrderUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(productModel: ProductModel) {
        repository.addProducts(productModel)
    }

}