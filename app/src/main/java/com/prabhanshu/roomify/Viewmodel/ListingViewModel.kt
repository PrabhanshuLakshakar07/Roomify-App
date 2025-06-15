package com.prabhanshu.roomify.Viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Modal.CreateListingRequest
import com.prabhanshu.roomify.Modal.Listing
import com.prabhanshu.roomify.Modal.Repo.ListingsRepository
import com.prabhanshu.roomify.Modal.Repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListingsViewModel @Inject constructor(
    private val repository: ListingsRepository
) : ViewModel() {

    private val _state = mutableStateOf(ListingsState())
    val state: State<ListingsState> = _state

    init {
        getListings()
    }

    fun onEvent(event: ListingsEvent) {
        when (event) {
            is ListingsEvent.Refresh -> {
                getListings()
            }
            is ListingsEvent.Search -> {
                getListings(
                    location = event.location,
                    minPrice = event.minPrice,
                    maxPrice = event.maxPrice
                )
            }
        }
    }
    fun createListing(token: String, request: CreateListingRequest): Flow<Resource<Listing>> {
        return repository.createListing(token, request)
    }
    private fun getListings(
        location: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ) {
        repository.getListings(location, minPrice, maxPrice).onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    _state.value = state.value.copy(
                        listings = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error<*> -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
data class ListingsState(
    val isLoading: Boolean = false,
    val listings: List<Listing> = emptyList(),
    val error: String = ""
)

sealed class ListingsEvent {
    object Refresh : ListingsEvent()
    data class Search(
        val location: String? = null,
        val minPrice: Double? = null,
        val maxPrice: Double? = null
    ) : ListingsEvent()
}