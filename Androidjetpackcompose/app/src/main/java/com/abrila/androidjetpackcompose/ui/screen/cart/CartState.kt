package com.abrila.androidjetpackcompose.ui.screen.cart

import com.abrila.androidjetpackcompose.model.CountOrder

data class CartState(
    val countOrder: List<CountOrder>,
    val totalPrice: Int
)