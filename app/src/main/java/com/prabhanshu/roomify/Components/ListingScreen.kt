package com.prabhanshu.roomify.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Viewmodel.ListingsEvent
import com.prabhanshu.roomify.Viewmodel.ListingsViewModel

@Composable
fun ListingsScreen(
    onNavigateToDetail: (String) -> Unit,
    searchQuery: String = "",
    selectedFilter: String = "All",
    viewModel: ListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    // Apply search when searchQuery changes
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            viewModel.onEvent(ListingsEvent.Search(location = searchQuery))
        } else {
            viewModel.onEvent(ListingsEvent.Refresh)
        }
    }

    // Apply filter when selectedFilter changes
    LaunchedEffect(selectedFilter) {
        // You can implement filter logic here based on your needs
        when (selectedFilter) {
            "Recent" -> {
                // Filter by recent listings
                viewModel.onEvent(ListingsEvent.Refresh)
            }
            "Favorites" -> {
                // Filter by favorites (you'll need to implement this)
                viewModel.onEvent(ListingsEvent.Refresh)
            }
            else -> {
                // Show all listings
                viewModel.onEvent(ListingsEvent.Refresh)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.error.isNotBlank() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.onEvent(ListingsEvent.Refresh) }
                    ) {
                        Text("Retry")
                    }
                }
            }
            state.listings.isEmpty() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No listings found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (searchQuery.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Try a different search term",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.listings) { listing ->
                        ListingItem(
                            listing = listing,
                            onItemClick = { onNavigateToDetail(listing._id) }
                        )
                    }
                }
            }
        }
    }
}
