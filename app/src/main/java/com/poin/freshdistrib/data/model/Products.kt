package com.poin.freshdistrib.data.model

/**
 *
 * Quantity val is a pair of the available quantity of the product and the unit (ml, g)
 *
 * Slot is the slot number where the product is in the vending maching
 */
data class Products(val name: String, val price: Double, val quantity: Pair<Int, String>, val slot: Int)
