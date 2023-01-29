package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentOrderUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val getDiscountUseCase: GetDiscountUseCase
) {

    operator fun invoke(): Flow<CurrentOrderModel> = flow {
        repository.getCurrentOrder().collect{
            emit(getProductGroup(it))
        }
    }

    fun getProductGroup(productList: List<ProductModel>): CurrentOrderModel {
        val map = productList.groupBy { it.code }.values.map { item ->
            val priceDiscount = getDiscountUseCase.getPriceDiscount(item[0].code, item[0].price, item.sumOf { it.amount })
            ProductModel(
                item[0].id,
                item[0].code,
                item[0].name,
                item[0].image,
                priceDiscount.price,
                null,
                item.sumOf { it.amount },
                priceDiscount.total
            ) }

        val currentOrderModel = CurrentOrderModel(products = emptyList())
        val finalList = ArrayList<ProductModel>()
        map.forEach {
            finalList.add(it)
            currentOrderModel.total += it.total
        }

        currentOrderModel.products = finalList.toList()

        return currentOrderModel
    }
}