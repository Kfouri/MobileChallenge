package com.kfouri.cabifychallenge.ui.productList

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kfouri.cabifychallenge.domain.model.ProductModel
import com.kfouri.cabifychallenge.R
import com.kfouri.cabifychallenge.ui.productList.model.ProductListUIState
import com.kfouri.cabifychallenge.util.Constants
import com.kfouri.cabifychallenge.util.Constants.ON_SALE_2X1
import com.kfouri.cabifychallenge.util.Constants.ON_SALE_DISCOUNT_MORE_THAN_THREE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(productListViewModel: ProductListViewModel) {

    val screenState by productListViewModel.uiState.collectAsState()

    if (screenState.init) {
        productListViewModel.getProducts()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (screenState.loading) {
            ShowLoading()
        }

        if (screenState.dialog) {
            screenState.productModel?.let { product ->
                ProductDialog(
                    product,
                    screenState,
                    onDismiss = { productListViewModel.onDismiss() },
                    onProductAdded = { amount ->
                        productListViewModel.onProductAdded(
                            product,
                            amount
                        )
                    },
                    onAmountDecrease = { productListViewModel.onAmountDecrease() },
                    onAmountIncrease = { productListViewModel.onAmountIncrease() }
                )
            }
        }

        if (screenState.success?.isNotEmpty() == true) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.products_list)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.cabify),
                        titleContentColor = Color.White
                    )
                )
            }, content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(screenState.success!!) { product ->
                        ProductItem(product, productListViewModel)
                    }
                }
            })

        }

        if (screenState.productAdded) {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.product_added_correctly),
                Toast.LENGTH_LONG
            ).show()
            productListViewModel.onMessageShowed()
        }

        if (screenState.error?.isNotEmpty() == true) {
            Toast.makeText(
                LocalContext.current,
                "Error: ${screenState.error}",
                Toast.LENGTH_LONG
            ).show()
            productListViewModel.onErrorShowed()
        }
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ProductItem(product: ProductModel, productListViewModel: ProductListViewModel) {

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
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                productListViewModel.onOpenDialog(product)
            }) {

            val (productImage, productName, productPrice, productDiscount, divider) = createRefs()

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_product),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(productImage) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )

            Text(
                text = product.name,
                modifier = Modifier.constrainAs(productName) {
                    top.linkTo(productImage.top)
                    start.linkTo(productImage.end, margin = 16.dp)
                },
                fontWeight = FontWeight.Bold
            )

            Text(text = "$" + product.price, modifier = Modifier.constrainAs(productPrice) {
                top.linkTo(productName.bottom, margin = 8.dp)
                start.linkTo(productImage.end, margin = 16.dp)
            })

            var image = 0
            var visible = false

            if (product.discount?.type == ON_SALE_2X1) {
                image = R.drawable.image_2x1
                visible = true
            } else if (product.discount?.type == ON_SALE_DISCOUNT_MORE_THAN_THREE) {
                image = R.drawable.image_discount
                visible = true
            }

            if (visible) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(productDiscount) {
                            top.linkTo(parent.top, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                )
            }

            Divider(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(divider) {
                        top.linkTo(productImage.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }
}

@Composable
fun ProductDialog(
    productModel: ProductModel,
    screenState: ProductListUIState,
    onDismiss: () -> Unit,
    onProductAdded: (Int) -> Unit,
    onAmountDecrease: () -> Unit,
    onAmountIncrease: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = productModel.name.uppercase(),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.size(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.price, screenState.price),
                    modifier = Modifier.padding(start = 16.dp)
                )
                screenState.discount?.let {
                    if (it.type != Constants.ON_SALE_NONE) {
                        Spacer(Modifier.weight(1f))
                        Pulsating {
                            Text(
                                text = it.discount,
                                modifier = Modifier.padding(end = 16.dp),
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.size(16.dp))
            Text(text = stringResource(R.string.amount), modifier = Modifier.padding(start = 16.dp))
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { onAmountDecrease() }) {
                    Text(text = "-", fontSize = 25.sp)
                }
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "${screenState.amount}",
                    fontSize = 40.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(Modifier.size(16.dp))
                Button(onClick = { onAmountIncrease() }) {
                    Text(text = "+", fontSize = 25.sp)
                }
            }

            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(R.string.total, screenState.total),
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))
            Divider()
            Button(
                onClick = {
                    onProductAdded(screenState.amount)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.add_to_shopping_cart))
            }

        }
    }
}

@Composable
fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}