package com.kfouri.cabifychallenge.ui.currentOrder.model

sealed interface CurrentOrderUiState {
    object Loading : CurrentOrderUiState
    data class Error(val throwable: Throwable) : CurrentOrderUiState
    data class Success(val data: CurrentOrderModel) : CurrentOrderUiState
}