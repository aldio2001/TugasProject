package com.abrila.androidjetpackcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrila.androidjetpackcompose.data.DrinkRepository
import com.abrila.androidjetpackcompose.model.CountOrder
import com.abrila.androidjetpackcompose.model.Drink
import com.abrila.androidjetpackcompose.ui.common.Hasil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DrinkRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<Hasil<List<CountOrder>>> = MutableStateFlow(Hasil.Loading)
    val uiState: StateFlow<Hasil<List<CountOrder>>>
        get() = _uiState

    private val _groupedHeroes = MutableStateFlow(
        repository.getDrinks()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    private val _query = mutableStateOf(" ")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedHeroes.value = repository.searchDrinks(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }

    fun getAllRewards() {
        viewModelScope.launch {
            repository.getAllRewards()
                .catch {
                    _uiState.value = Hasil.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = Hasil.Success(orderRewards)
                }
        }
    }
}