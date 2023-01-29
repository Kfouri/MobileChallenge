package com.kfouri.cabifychallenge.ui.currentOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfouri.cabifychallenge.domain.BuyProductsUseCase
import com.kfouri.cabifychallenge.domain.GetCurrentOrderUseCase
import com.kfouri.cabifychallenge.domain.GetDiscountUseCase
import com.kfouri.cabifychallenge.domain.RemoveProductOrderUseCase
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderModel
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentOrderViewModel@Inject constructor(
    private val getCurrentOrderUseCase: GetCurrentOrderUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val buyProductsUseCase: BuyProductsUseCase,
    private val removeProductOrderUseCase: RemoveProductOrderUseCase
) : ViewModel() {

    val uiState: StateFlow<CurrentOrderUiState> =
        getCurrentOrderUseCase().map(CurrentOrderUiState::Success)
            .catch { CurrentOrderUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CurrentOrderUiState.Loading
            )

    fun onBuy() {
        viewModelScope.launch(Dispatchers.IO) {
            buyProductsUseCase.invoke()
        }
    }

    fun getItemsOrderCount(list: List<ProductModel>): Int {
        val currentOrderModel = getCurrentOrderUseCase.getProductGroup(list)
        var productCount = 0
        currentOrderModel.products.forEach { item ->
            productCount += item.amount
        }
        return productCount
    }

    fun removeProduct(productModel: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductOrderUseCase.invoke(productModel)
        }
    }
}