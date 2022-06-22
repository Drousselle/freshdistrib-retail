package com.poin.freshdistrib.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.poin.freshdistrib.data.model.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewmodel : ViewModel() {

    private var _listProducts = MutableStateFlow<List<Products>>(emptyList())
    val listProducts: StateFlow<List<Products>>
        get() = _listProducts

    private var _productsInCart = MutableStateFlow<List<Products>>(emptyList())
    val productsInCart: StateFlow<List<Products>>
        get() = _productsInCart

    fun setListProducts(products: List<Products>){
        _listProducts.value = products.sortedBy { it.slot }
    }

    fun setProductsInCart(products: List<Products>){
        _productsInCart.value = products.sortedBy { it.slot }
    }

    private fun deleteProductFromList(products: Products){
        val mutableListProducts = listProducts.value.toMutableList()
        mutableListProducts.remove(products)
        setListProducts(mutableListProducts.toList())
    }

    fun addProductToCart(product: Products){
        val mutableListProductsInCart = productsInCart.value.toMutableList()
        mutableListProductsInCart.add(product)
        setProductsInCart(mutableListProductsInCart.toList())
        deleteProductFromList(product)
    }

    fun deleteCart(){
        val mutableListProducts = listProducts.value.toMutableList()
        val mutableListProductsInCart = productsInCart.value.toMutableList()
        mutableListProducts.addAll(productsInCart.value)
        mutableListProductsInCart.clear()
        setListProducts(mutableListProducts.toList())
        setProductsInCart(mutableListProductsInCart.toList())
    }

    fun deleteSingleProductFromCart(product: Products){
        val mutableListProducts = listProducts.value.toMutableList()
        val mutableListProductsInCart = productsInCart.value.toMutableList()
        mutableListProductsInCart.remove(product)
        mutableListProducts.add(product)
        setListProducts(mutableListProducts.toList())
        setProductsInCart(mutableListProductsInCart.toList())
    }

}