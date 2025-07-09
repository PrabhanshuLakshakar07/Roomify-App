package com.prabhanshu.roomify.Viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Model.Data.CreateListingRequest
import com.prabhanshu.roomify.Model.Data.Listing
import com.prabhanshu.roomify.Model.Data.UpdateListingRequest
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Model.Repo.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repository: ListingRepository,
    private val prefs: PreferencesManager
) : ViewModel() {



    var listings by mutableStateOf<List<Listing>>(emptyList())
    var myListings by mutableStateOf<List<Listing>>(emptyList())
    var selectedListing by mutableStateOf<Listing?>(null)
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var createSuccess by mutableStateOf(false)


    init { fetchAll() }

    fun fetchAll() {
        viewModelScope.launch {
            isLoading = true
            try {
                listings = repository.getAllListings()
            } catch (e: Exception) {
                error = e.localizedMessage
            } finally { isLoading = false }
        }
    }

    fun fetchMyListings() {
        viewModelScope.launch {
            isLoading = true
            try {
                val token = prefs.getAuthToken() ?: return@launch
                myListings = repository.getMyListings(token)
            } catch (e: Exception) {
                error = e.localizedMessage
            } finally { isLoading = false }
        }
    }

    fun createListing(listing: CreateListingRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val token = prefs.getAuthToken() ?: return@launch
                repository.create(token, listing)
                fetchMyListings()
                createSuccess = true
                onSuccess()
            } catch (e: Exception) {
                error = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
    fun resetCreateSuccess() {
        createSuccess = false
    }
    fun updateListing(id: String, body: UpdateListingRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val token = prefs.getAuthToken() ?: return@launch
                repository.update(token, id, body)
                fetchMyListings()
                onSuccess()
            } catch (e: Exception) {
                error = e.localizedMessage
            }
        }
    }

    fun deleteListing(id: String) {
        viewModelScope.launch {
            try {
                val token = prefs.getAuthToken() ?: return@launch
                repository.delete(token, id)
                fetchMyListings()
            } catch (e: Exception) {
                error = e.localizedMessage
            }
        }
    }

    fun getListingById(id: String) {
        viewModelScope.launch {
            try {
                selectedListing = repository.getListing(id)
            } catch (e: Exception) {
                error = e.localizedMessage
            }
        }
    }


}
data class ListingsUiState(
    val isLoading: Boolean = false,
    val listings: List<Listing> = emptyList(),
    val error: String? = null,
    val isCreating: Boolean,
    val createSuccess: Boolean
)