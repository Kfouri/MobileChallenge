package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import javax.inject.Inject

class BuyProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke() {
        repository.buyProducts()
    }

}