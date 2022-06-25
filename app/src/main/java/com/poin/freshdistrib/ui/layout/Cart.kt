package com.poin.freshdistrib.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.poin.freshdistrib.domain.viewmodel.PaymentStatus
import com.poin.freshdistrib.ui.theme.freshdistrib_green
import com.poin.freshdistrib.ui.theme.freshdistrib_orange
import com.poin.freshdistrib.ui.theme.freshdistrib_red
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartSheet() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Box(modifier = Modifier
                .height(310.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()){
                Column {
                    ListProducts()
                }
            }
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.White
        ) {
        Scaffold(
            content = {
               MainContent()
            }
        )
    }
}

@Composable
private fun EmptyCart(){
    Box(modifier = Modifier.padding(15.dp)) {
        Text(text = "Votre panier est vide", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ListProducts(viewModel: MainViewmodel = viewModel()){
    val products by viewModel.productsInCart.collectAsState()
    if(products.isEmpty()){
        EmptyCart()
    } else {
        products.forEach {
            ProductInCart(product = it)
        }
        TotalToPay()
        ValidateCart()
    }
}

@Composable
private fun ProductInCart(product: Products, viewModel: MainViewmodel = viewModel()){
    val context = LocalContext.current
    val image = context.resources.getIdentifier(product.image, "drawable", context.packageName);
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F)){
            Image(painter = painterResource(id = image) ,
                contentDescription = "product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                ,
            )
        }

        Text(text = "${product.name}", modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .weight(1F))
        Text(text = "${product.quantity.first} ${product.quantity.second}", modifier = Modifier
            .weight(1F))
        Text(text = "${product.price} €", modifier = Modifier
            .weight(1F))
        IconButton(modifier = Modifier.
        then(Modifier.size(24.dp)),onClick = { viewModel.deleteSingleProductFromCart(product) }) {
            Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = "delete item from cart", modifier = Modifier
                .width(100.dp)
                .height(25.dp)
                .weight(1F), tint = freshdistrib_red)
        }
    }
}

@Composable
private fun TotalToPay(viewModel: MainViewmodel = viewModel()) {
    val total by viewModel.totalToPay.collectAsState()
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(text = "Total : $total €", color = freshdistrib_red, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ValidateCart(viewModel: MainViewmodel = viewModel()){
    val showDialog by viewModel.showDialog.collectAsState()
    if (showDialog) {
        PaymentDialog()
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Button(onClick = {
            viewModel.setShowDialog(true)
            viewModel.setPaymentStatus(PaymentStatus.LOADING)
                         },
            colors = ButtonDefaults.buttonColors(
            freshdistrib_green), modifier = Modifier.padding(bottom = 10.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = stringResource(id = R.string.validate), color = Color.White)
        }
    }
}



