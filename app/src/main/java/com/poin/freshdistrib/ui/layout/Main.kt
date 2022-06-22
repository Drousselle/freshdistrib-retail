package com.poin.freshdistrib.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poin.freshdistrib.R
import com.poin.freshdistrib.data.model.Products
import com.poin.freshdistrib.domain.viewmodel.MainViewmodel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        HeaderText()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        ProductsList()
    }
}

@Composable
fun HeaderText(){
    Text(text = stringResource(id = R.string.product_selection))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsList(viewmodel: MainViewmodel = viewModel()) {
    val products by viewmodel.listProducts.collectAsState()
    val productsInCart by viewmodel.productsInCart.collectAsState()
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 150.dp)) {
        items(products) {
            ProductCard(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCard(product: Products, viewmodel: MainViewmodel = viewModel()){
    val paddingModifier  = Modifier.padding(10.dp)
            Card(elevation = 10.dp, modifier = paddingModifier, onClick = { viewmodel.addProductToCart(product)}) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.name,
                        modifier = paddingModifier
                    )
                    Text(
                        text = "Quantitée : ${product.quantity.first} ${product.quantity.second}"
                    )
                    Text(
                        text = "Prix : ${product.price}"
                    )
                    Text(
                        text = "Slot : ${product.slot}"
                    )
                }
            }
        }

