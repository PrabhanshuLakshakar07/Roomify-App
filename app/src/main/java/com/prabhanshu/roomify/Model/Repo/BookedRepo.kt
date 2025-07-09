package com.prabhanshu.roomify.Model.Repo

import com.prabhanshu.roomify.Model.Data.BookingResponse
import com.prabhanshu.roomify.Model.Network.ApiService
import javax.inject.Inject

class BookedRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMyBookings(token: String): List<BookingResponse> {
        return apiService.getMyBookings("Bearer $token")
    }

    suspend fun deleteBooking(token: String, bookingId: String) {
        apiService.deleteBooking(bookingId, "Bearer $token")
    }
}