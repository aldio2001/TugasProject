package com.abrila.androidjetpackcompose.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrila.androidjetpackcompose.data.DrinkRepository
import com.abrila.androidjetpackcompose.ui.common.Hasil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: DrinkRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<Hasil<CartState>> = MutableStateFlow(Hasil.Loading)
    val uiState: StateFlow<Hasil<CartState>>
        get() = _uiState

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = Hasil.Loading
            repository.getAddedOrderRewards()
                .collect { orderReward ->
                    val totalRequiredPoint =
                        orderReward.sumOf { it.drink.price * it.count }
                    _uiState.value = Hasil.Success(CartState(orderReward, totalRequiredPoint))
                }
        }
    }

    fun updateOrderReward(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRewards()
                    }
                }
        }
    }
}