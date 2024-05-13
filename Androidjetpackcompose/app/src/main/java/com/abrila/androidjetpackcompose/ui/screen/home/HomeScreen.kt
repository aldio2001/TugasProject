package com.abrila.androidjetpackcompose.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrila.androidjetpackcompose.R
import com.abrila.androidjetpackcompose.di.Injection
import com.abrila.androidjetpackcompose.model.CountOrder
import com.abrila.androidjetpackcompose.ui.ViewModelFactory
import com.abrila.androidjetpackcompose.ui.common.Hasil
import com.abrila.androidjetpackcompose.ui.componen.DrinkItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = Hasil.Loading).value.let { uiState ->
        when (uiState) {
            is Hasil.Loading -> {
                viewModel.getAllRewards()
            }
            is Hasil.Success -> {
                HomeContent(
                    countOrder = uiState.data,
                    query = viewModel.query,
                    onQueryChange = viewModel::search,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is Hasil.Error -> {}
        }
    }
}

@Composable

fun HomeContent(
    countOrder: List<CountOrder>,
    query: State<String>,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        )

        // Mengubah `columns` menjadi GridCells.Fixed(2)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
        ) {
            val filteredCountOrder = countOrder.filter {
                it.drink.name.contains(query.value, ignoreCase = true)
            }
            // Menyesuaikan jumlah item yang dihasilkan menjadi 20 (2 kolom * 10 baris)
            items(filteredCountOrder.take(30)) { data ->
                DrinkItem(
                    image = data.drink.photoUrl,
                    title = data.drink.name,
                    requiredPoint = data.drink.price,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.drink.id)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: State<String>,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query.value,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_hero))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}