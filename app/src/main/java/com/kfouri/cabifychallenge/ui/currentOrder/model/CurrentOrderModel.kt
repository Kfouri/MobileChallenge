package com.kfouri.cabifychallenge.ui.currentOrder.model

import com.kfouri.cabifychallenge.domain.model.ProductModel

data class CurrentOrderModel(
    var products: List<ProductModel>,
    var total: Float = 0f,
    val discount: Float = 0f
)