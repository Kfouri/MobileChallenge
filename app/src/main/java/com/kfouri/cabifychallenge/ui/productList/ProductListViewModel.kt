package com.kfouri.cabifychallenge.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfouri.cabifychallenge.data.model.Resource
import com.kfouri.cabifychallenge.domain.AddProductOrderUseCase
import com.kfouri.cabifychallenge.domain.GetDiscountUseCase
import com.kfouri.cabifychallenge.domain.GetProductListUseCase
import com.kfouri.cabifychallenge.domain.model.PriceDiscount
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.ui.productList.model.ProductListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val addProductOrderUseCase: AddProductOrderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUIState())
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    fun getProducts() {

        viewModelScope.launch {
            showLoading()
            when(val response = getProductListUseCase.invoke()){
                is Resource.Success -> {
                    setSuccessful(response.data?.products)
                }
                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    private fun showError(message: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                error = message
            )
        }
    }

    private fun showLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                loading = true,
                init = false
            )
        }
    }

    private fun setSuccessful(productList: List<ProductModel>?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                success = productList,
                productAdded = false
            )
        }
    }

    fun onProductAdded(productModel: ProductModel, amount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dialog = false
            )
        }

        productModel.amount = amount

        viewModelScope.launch(Dispatchers.IO) {
            addProductOrderUseCase(productModel)
        }

        _uiState.update { currentState ->
            currentState.copy(
                productAdded = true
            )
        }
    }

    fun onDismiss() {
        _uiState.update { currentState ->
            currentState.copy(
                dialog = false
            )
        }
    }

    private fun getTotal(productModel: ProductModel, amount: Int): PriceDiscount {
        return getDiscountUseCase.getPriceDiscount(productModel.code, productModel.price, amount)
    }

    fun onOpenDialog(productModel: ProductModel) {

        val priceDiscount = getTotal(productModel, 1)

        _uiState.update { currentState ->
            currentState.copy(
                dialog = true,
                productModel = productModel,
                price = productModel.price,
                amount = 1,
                total = priceDiscount.total,
                productAdded = false,
                discount = getDiscountUseCase.applyDiscount(productModel.code)
            )
        }
    }

    fun onAmountDecrease() {
        _uiState.update { currentState ->
            val newAmount = if (currentState.amount > 1) currentState.amount - 1 else 1
            val total = currentState.productModel?.let {
                getTotal(it, newAmount)
            } ?: run { 0 }.toFloat()

            val priceDiscount = currentState.productModel?.let { getTotal(it, newAmount) }
                ?: run { PriceDiscount(0f, 0f) }

            currentState.copy(
                amount = newAmount,
                total = priceDiscount.total,
                price = priceDiscount.price
            )
        }
    }

    fun onAmountIncrease() {
        _uiState.update { currentState ->
            val newAmount = currentState.amount + 1
            val total = currentState.productModel?.let {
                getTotal(it, newAmount)
            } ?: run { 0 }.toFloat()

            val priceDiscount = currentState.productModel?.let { getTotal(it, newAmount) }
                ?: run { PriceDiscount(0f, 0f) }

            currentState.copy(
                amount = newAmount,
                total = priceDiscount.total,
                price = priceDiscount.price
            )
        }
    }

    fun onMessageShowed() {
        _uiState.update { currentState ->
            currentState.copy(
                productAdded = false
            )
        }
    }

    fun onErrorShowed() {
        _uiState.update { currentState ->
            currentState.copy(
                error = null
            )
        }
    }
}