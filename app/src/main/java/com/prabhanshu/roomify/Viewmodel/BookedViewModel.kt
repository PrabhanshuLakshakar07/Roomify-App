package com.prabhanshu.roomify.Viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Model.Data.BookingResponse
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Model.Repo.BookedRepository
import com.prabhanshu.roomify.Model.Repo.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookedViewModel @Inject constructor(
    private val bookingRepository: BookedRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var bookings by mutableStateOf<List<BookingResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)

    fun fetchBookings() {
        viewModelScope.launch {
            isLoading = true
            try {
                val token = preferencesManager.getAuthToken() ?: return@launch
                bookings = bookingRepository.getMyBookings(token)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteBooking(bookingId: String) {
        viewModelScope.launch {
            try {
                val token = preferencesManager.getAuthToken() ?: return@launch
                bookingRepository.deleteBooking(token, bookingId)
                fetchBookings() // refresh list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
