package com.poin.freshdistrib.ui.layout

import android.os.CountDownTimer
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poin.freshdistrib.R
import com.poin.freshdistrib.domain.viewmodel.MainViewmodel
import com.poin.freshdistrib.domain.viewmodel.PaymentStatus
import com.poin.freshdistrib.ui.theme.freshdistrib_green
import com.poin.freshdistrib.ui.theme.freshdistrib_red

@Composable
fun PaymentDialog(viewmodel: MainViewmodel = viewModel()){
    val paymentStatus by viewmodel.paymentStatus.collectAsState()
    Dialog(
        onDismissRequest = { dismissDialog(paymentStatus, viewmodel) },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        DialogContent()
    }
}

private fun dismissDialog(paymentStatus: PaymentStatus, viewmodel: MainViewmodel){
    if(paymentStatus == PaymentStatus.SUCCESS){
        viewmodel.deleteCart()
    }
    viewmodel.setShowDialog(false)

}

@Composable
private fun DialogContent(viewmodel: MainViewmodel = viewModel()){
    val paymentStatus by viewmodel.paymentStatus.collectAsState()
    CustomCard {
        DialogHeader()
        Box(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .height(250.dp)
        ) {
            when (paymentStatus) {
                PaymentStatus.LOADING -> PaymentLoading()
                PaymentStatus.SUCCESS -> PaymentSuccess()
                PaymentStatus.ERROR -> PaymentError()
            }
        }
    }
}

@Composable
private fun PaymentLoading(){
    CircularProgress()
}

@Composable
private fun PaymentSuccess() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_valid),
                contentDescription = "payment success",
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp),
                tint = freshdistrib_green
            )
            Text(text = stringResource(id = R.string.payment_success),textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = freshdistrib_green, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth())
        }
}

@Composable
private fun PaymentError(){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "payment error",
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp),
                tint = freshdistrib_red
            )
            Text(text = stringResource(id = R.string.payment_error),textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = freshdistrib_red, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth())
        }
}

@Composable
private fun DialogHeader(viewmodel: MainViewmodel = viewModel()){
    val paymentStatus by viewmodel.paymentStatus.collectAsState()
    val paddingModifier = Modifier.padding(10.dp)
    Box(contentAlignment = Alignment.CenterEnd) {
        CardHeader(modifier = paddingModifier.fillMaxWidth(), title = "Confirmation")
        IconButton(modifier = Modifier.
        then(Modifier.size(40.dp)), onClick = { dismissDialog(paymentStatus, viewmodel) }) {
            Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription = "close button", tint = Color.White,  modifier = Modifier
                .width(100.dp)
                .height(35.dp)
                .padding(end = 15.dp)
            )
        }
    }
}

@Composable
private fun CircularProgress(viewmodel: MainViewmodel = viewModel()){
    CircularProgressIndicator(color = freshdistrib_green, modifier = Modifier
        .width(200.dp)
        .height(150.dp)
        .padding(top = 20.dp)
    )
    object : CountDownTimer(5000, 1000) {

        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            val rnds = (0..10).random()
            if(rnds in 0..5){
                viewmodel.setPaymentStatus(PaymentStatus.SUCCESS)
            }else {
                viewmodel.setPaymentStatus(PaymentStatus.ERROR)
            }
        }
    }.start()
}