package com.kfouri.cabifychallenge.ui.currentOrder

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kfouri.cabifychallenge.R
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderModel
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentOrderScreen(currentOrderViewModel: CurrentOrderViewModel) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<CurrentOrderUiState>(
        initialValue = CurrentOrderUiState.Loading,
        key1 = lifecycle,
        key2 = currentOrderViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            currentOrderViewModel.uiState.collect {
                value = it
            }
        }
    }

    when (uiState) {
        is CurrentOrderUiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                "Error: ${(uiState as CurrentOrderUiState.Error).throwable.message}",
                Toast.LENGTH_LONG
            ).show()
        }
        CurrentOrderUiState.Loading -> {
            CircularProgressIndicator()
        }
        is CurrentOrderUiState.Success -> {

            val currentOrderModel = (uiState as CurrentOrderUiState.Success).data

            Scaffold(topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.current_order)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.cabify),
                        titleContentColor = Color.White
                    )
                )
            }, content = { innerPadding ->
                if (currentOrderModel.products.isNotEmpty()) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {

                        Spacer(Modifier.size(24.dp))
                        ProductList(currentOrderModel, currentOrderViewModel)
                        Spacer(Modifier.size(16.dp))
                        Button(
                            onClick = {
                                currentOrderViewModel.onBuy()
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                stringResource(
                                    R.string.current_order_buy
                                )
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            stringResource(
                                R.string.current_order_empty_cart
                            ), modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            })
        }
    }
}

@Composable
fun ProductList(
    currentOrderModel: CurrentOrderModel,
    currentOrderViewModel: CurrentOrderViewModel
) {
    LazyColumn {
        item {
            HeaderList()
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
        items(currentOrderModel.products!!, key = { it.id }) { product ->
            ItemProduct(product, currentOrderViewModel)
        }
        item {
            FooterList(currentOrderModel.total)
        }
    }
}

@Composable
fun FooterList(total: Float) {
    Spacer(modifier = Modifier.size(8.dp))
    Divider(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
    Spacer(modifier = Modifier.size(8.dp))
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Total $$total", modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp),
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun HeaderList() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                R.string.current_order_product_name
            ), modifier = Modifier.weight(4f), fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = stringResource(
                R.string.current_order_amount
            ),
            modifier = Modifier.weight(1.5f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = stringResource(
                R.string.current_order_price
            ),
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = stringResource(
                R.string.current_order_total
            ),
            modifier = Modifier.weight(2f),
            fontSize = 14.sp,
            textAlign = TextAlign.End
        )
        Text(
            text = "",
            modifier = Modifier.weight(0.5f)
        )
    }

}

@Composable
fun ItemProduct(productModel: ProductModel, currentOrderViewModel: CurrentOrderViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, colorResource(R.color.cabify))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = productModel.name, modifier = Modifier.weight(4f), fontSize = 14.sp)
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${productModel.amount}",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${productModel.price}",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${productModel.total}",
                modifier = Modifier.weight(2f),
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.size(2.dp))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        currentOrderViewModel.removeProduct(productModel)
                    }
                    .weight(0.5f)
            )
        }

    }

}