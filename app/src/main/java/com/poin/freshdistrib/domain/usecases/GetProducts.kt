package com.poin.freshdistrib.domain.usecases

import android.content.Context
import com.google.gson.Gson
import com.poin.freshdistrib.R
import com.poin.freshdistrib.data.model.Products
import com.poin.freshdistrib.domain.viewmodel.MainViewmodel

class GetProducts {
    private fun getProductsFromJson(context: Context) : List<Products> {
        val fileContent = context.resources.openRawResource(R.raw.products).bufferedReader().readText()
        val gson = Gson()
        val getProducts = gson.fromJson(fileContent, Array<Products>::class.java)
        return getProducts.toList()
    }

    fun handleProductsInit(context: Context, viewmodel: MainViewmodel){
        saveProduct(viewmodel, getProductsFromJson(context))
    }

    private fun saveProduct(viewmodel: MainViewmodel, products: List<Products>){
        viewmodel.setListProducts(products)
    }
}