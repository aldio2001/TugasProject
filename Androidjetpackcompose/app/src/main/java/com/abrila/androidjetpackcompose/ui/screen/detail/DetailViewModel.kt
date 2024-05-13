package com.abrila.androidjetpackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrila.androidjetpackcompose.data.DrinkRepository
import com.abrila.androidjetpackcompose.model.CountOrder
import com.abrila.androidjetpackcompose.model.Drink
import com.abrila.androidjetpackcompose.ui.common.Hasil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DrinkRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<Hasil<CountOrder>> =
        MutableStateFlow(Hasil.Loading)
    val uiState: StateFlow<Hasil<CountOrder>>
        get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = Hasil.Loading
            _uiState.value = Hasil.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(drink: Drink, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(drink.id, count)
        }
    }
}