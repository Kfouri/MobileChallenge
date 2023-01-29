package com.kfouri.cabifychallenge.ui.productList.model

import com.kfouri.cabifychallenge.domain.model.Discount
import com.kfouri.cabifychallenge.domain.model.ProductModel

data class ProductListUIState(
    val loading: Boolean = false,
    val success: List<ProductModel>? = emptyList(),
    val error: String? = null,
    val init: Boolean = true,
    val dialog: Boolean = false,
    val productModel: ProductModel? = null,
    val amount: Int = 0,
    val productAdded: Boolean = false,
    val total: Float = 0f,
    val price: Float = 0f,
    val discount: Discount? = null
)