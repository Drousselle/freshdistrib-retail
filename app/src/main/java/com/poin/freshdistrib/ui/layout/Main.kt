package com.poin.freshdistrib.ui.layout

import android.icu.text.CaseMap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.poin.freshdistrib.ui.theme.freshdistrib_red

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPage() {
    CartSheet()

}

@Composable
fun MainContent(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        FreshDistribLogo()
        Spacer(modifier = Modifier.padding(top = 20.dp))
        ProductsList()
    }
}

@Composable
private fun FreshDistribLogo(){
    Image(painterResource(R.drawable.freshdistriblogo),"company logo", modifier = Modifier.width(200.dp), alignment = Alignment.Center)
}

@Composable
private fun HeaderText() {
    Text(text = stringResource(id = R.string.product_selection), fontWeight = FontWeight.Bold)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductsList(viewmodel: MainViewmodel = viewModel()) {
    val products by viewmodel.listProducts.collectAsState()

    if(products.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.no_product), fontWeight = FontWeight.Bold, color = freshdistrib_red, textAlign = TextAlign.Center)
        }
    }
    HeaderText()
    Spacer(modifier = Modifier.padding(top = 40.dp))
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 150.dp), modifier = Modifier.padding(bottom = 60.dp)) {
        items(products) {
            CustomCard() {
                CardContent(product = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomCard(content: @Composable ColumnScope.() -> Unit) {
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        elevation = 10.dp,
        modifier = paddingModifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Composable
private fun CardContent(product: Products){
    val paddingModifier = Modifier.padding(10.dp)
    CardHeader(modifier = paddingModifier.fillMaxWidth(), title = product.name)
    CardImage(product = product)
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
    CardSlot(product = product)
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)) {
        CardQuantity(product = product, Modifier.weight(1f))
        CardPrice(product = product)
    }
    AddToCartButton(product = product)
}

@Composable
private fun CardImage(product: Products){
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
private fun AddToCartButton(product: Products, viewmodel: MainViewmodel = viewModel()){
    Button(onClick = { viewmodel.addProductToCart(product) }, colors = ButtonDefaults.buttonColors(
        freshdistrib_green), modifier = Modifier.padding(bottom = 10.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(text = stringResource(id = R.string.add_to_cart), color = Color.White)
    }
}

@Composable
fun CardHeader(modifier: Modifier, title: String) {
    Box(Modifier.background(freshdistrib_green)){
        Text(text = title, modifier = modifier, textAlign = TextAlign.Center,fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
private fun CardSlot(product: Products){
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
private fun CardPrice(product: Products){
    Text(text = "${product.price}€", textAlign = TextAlign.End, fontWeight = FontWeight.Bold, color = freshdistrib_red)
}

@Composable
private fun CardQuantity(product: Products, modifier: Modifier){
    Text(text = "${product.quantity.first} ${product.quantity.second}", modifier = modifier, fontWeight = FontWeight.Bold)
}
