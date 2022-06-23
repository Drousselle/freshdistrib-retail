package com.poin.freshdistrib.ui.layout

import android.content.res.Resources
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.name
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poin.freshdistrib.R
import com.poin.freshdistrib.data.model.Products
import com.poin.freshdistrib.domain.viewmodel.MainViewmodel
import com.poin.freshdistrib.ui.theme.freshdistrib_green
import com.poin.freshdistrib.ui.theme.freshdistrib_orange
import com.poin.freshdistrib.ui.theme.freshdistrib_red

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        FreshDistribLogo()
        Spacer(modifier = Modifier.padding(top = 20.dp))
        HeaderText()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        ProductsList()
    }
}

@Composable
fun FreshDistribLogo(){
    Image(painterResource(R.drawable.freshdistriblogo),"company logo", modifier = Modifier.width(200.dp), alignment = Alignment.Center)
}

@Composable
fun HeaderText() {
    Text(text = stringResource(id = R.string.product_selection), fontWeight = FontWeight.Bold)
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
fun ProductCard(product: Products, viewmodel: MainViewmodel = viewModel()) {
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        elevation = 10.dp,
        modifier = paddingModifier,
        onClick = { viewmodel.addProductToCart(product) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CardTitle(product = product, modifier = paddingModifier.fillMaxWidth())
            CardImage(product = product)
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            CardSlot(product = product)
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)) {
                    CardQuantity(product = product, Modifier.weight(1f))
                    CardPrice(product = product)
            }
        }
    }
}

@Composable
fun CardImage(product: Products){
    val context = LocalContext.current
    val image = context.resources.getIdentifier(product.image, "drawable", context.packageName);
    Image(
        painterResource(id = image),
        contentDescription = "product image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}

@Composable
fun CardTitle(product: Products, modifier: Modifier) {
    Box(Modifier.background(freshdistrib_green)){
        Text(text = product.name, modifier = modifier, textAlign = TextAlign.Center,fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun CardSlot(product: Products){
    Box(contentAlignment= Alignment.Center,
        modifier = Modifier
            .background(Color.White, shape = CircleShape)
            .border(1.dp, Color.Gray, CircleShape)
    ) {

        Text(
            text = "${product.slot}",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(24.dp) //Use a min size for short text.

        )
    }
}

@Composable
fun CardPrice(product: Products){
    Text(text = "${product.price}€", textAlign = TextAlign.End, fontWeight = FontWeight.Bold, color = freshdistrib_red)
}

@Composable
fun CardQuantity(product: Products, modifier: Modifier){
    Text(text = "${product.quantity.first} ${product.quantity.second}", modifier = modifier, fontWeight = FontWeight.Bold)
}
