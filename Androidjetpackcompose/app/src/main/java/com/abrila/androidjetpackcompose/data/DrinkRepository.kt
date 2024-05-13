package com.abrila.androidjetpackcompose.data

import com.abrila.androidjetpackcompose.model.CountOrder
import com.abrila.androidjetpackcompose.model.Drink
import com.abrila.androidjetpackcompose.model.DrinkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DrinkRepository {

    private val countOrders = mutableListOf<CountOrder>()

    init {
        if (countOrders.isEmpty()) {
            DrinkData.drinks.forEach {
                countOrders.add(CountOrder(it, 0))
            }
        }
    }

    fun getDrinks(): List<Drink> {
        return DrinkData.drinks
    }

    fun searchDrinks(query: String): List<Drink>{
        return DrinkData.drinks.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getAllRewards(): Flow<List<CountOrder>> {
        return flowOf(countOrders)
    }

    fun getOrderRewardById(drinkId: Long): CountOrder {
        return countOrders.first {
            it.drink.id == drinkId
        }
    }

    fun updateOrderReward(rewardId: Long, newCountValue: Int): Flow<Boolean> {
        val index = countOrders.indexOfFirst { it.drink.id == rewardId }
        val result = if (index >= 0) {
            val countOrder = countOrders[index]
            countOrders[index] =
                countOrder.copy(drink = countOrder.drink, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<CountOrder>> {
        return getAllRewards()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: DrinkRepository? = null

        fun getInstance(): DrinkRepository =
            instance ?: synchronized(this) {
                DrinkRepository().apply {
                    instance = this
                }
            }
    }
}