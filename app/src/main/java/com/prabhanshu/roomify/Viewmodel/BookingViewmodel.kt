package com.prabhanshu.roomify.Viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Model.Data.BookingResponse
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Model.Network.Request.BookingRequest
import com.prabhanshu.roomify.Model.Repo.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var bookingState by mutableStateOf<UiState<List<BookingResponse>>>(UiState.Loading)
        private set

    fun createBooking(token: String?, listingId: String, checkIn: String, checkOut: String) {
        val token = preferencesManager.getAuthToken() ?: return

        viewModelScope.launch {
            try {
                Log.d("BookingViewModel", "Creating booking with token: $token")
                bookingRepository.createBooking(
                    token,  // ✅ Add "Bearer " here
                    BookingRequest(listingId, checkIn, checkOut)
                )
                Log.d("BookingViewModel", "Booking created successfully")
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Booking failed: ${e.message}", e)
            }
        }
    }
}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}