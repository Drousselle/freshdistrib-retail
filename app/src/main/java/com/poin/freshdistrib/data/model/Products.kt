package com.poin.freshdistrib.data.model

/**
 *
 * Quantity val is a pair of the available quantity of the product and the unit (ml, g)
 *
 */
data class Products(val name: String, val price: Double, val quantity: Pair<Int, String>)
