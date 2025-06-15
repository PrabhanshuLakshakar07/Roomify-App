package com.prabhanshu.roomify.Viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Modal.Listing
import com.prabhanshu.roomify.Modal.Repo.ListingsRepository
import com.prabhanshu.roomify.Modal.Repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val repository: ListingsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ListingDetailState())
    val state: State<ListingDetailState> = _state

    init {
        savedStateHandle.get<String>("listingId")?.let { listingId ->
            getListingDetail(listingId)
        }
    }

    private fun getListingDetail(listingId: String) {
        repository.getListingById(listingId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        listing = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class ListingDetailState(
    val isLoading: Boolean = false,
    val listing: Listing? = null,
    val error: String = ""
)