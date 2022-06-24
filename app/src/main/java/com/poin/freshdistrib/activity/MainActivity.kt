package com.poin.freshdistrib.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.poin.freshdistrib.domain.usecases.GetProducts
import com.poin.freshdistrib.domain.viewmodel.MainViewmodel
import com.poin.freshdistrib.ui.layout.CartSheet
import com.poin.freshdistrib.ui.layout.MainPage
import com.poin.freshdistrib.ui.theme.FreshdistribTheme

class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        setContent {
            FreshdistribTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainPage()
                }
            }
        }
        initProductsData()
    }

    private fun hideSystemBars() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun initProductsData(){
        GetProducts().handleProductsInit(this, viewmodel)
    }
}