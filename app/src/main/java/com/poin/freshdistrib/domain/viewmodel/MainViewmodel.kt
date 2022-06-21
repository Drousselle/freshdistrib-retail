package com.poin.freshdistrib.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.poin.freshdistrib.data.model.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.poin.freshdistrib.domain.viewmodel.UpdateProduct.INCREMENTATION

class MainViewmodel : ViewModel() {

    private var _listProducts = MutableStateFlow<List<Products>>(emptyList())
    val listProducts: StateFlow<List<Products>>
        get() = _listProducts

    fun setListProducts(products: List<Products>){
        _listProducts.value = products.sortedBy { it.name }
    }

    fun changeProductsQuantity(products: Products, updateProduct: UpdateProduct){
        val mutableListProducts = listProducts.value.toMutableList()
        val update = if(updateProduct == INCREMENTATION) {
            products.copy(quantity = Pair(products.quantity.first+1, products.quantity.second))
        } else {
            products.copy(quantity = Pair(products.quantity.first-1, products.quantity.second))
        }
        mutableListProducts[mutableListProducts.indexOf(products)] = update
        setListProducts(mutableListProducts.toList())
    }



}

enum class UpdateProduct {
    INCREMENTATION,
    DECREMENTATION
}