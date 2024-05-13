package com.abrila.androidjetpackcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrila.androidjetpackcompose.data.DrinkRepository
import com.abrila.androidjetpackcompose.ui.screen.cart.CartViewModel
import com.abrila.androidjetpackcompose.ui.screen.detail.DetailViewModel
import com.abrila.androidjetpackcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: DrinkRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}