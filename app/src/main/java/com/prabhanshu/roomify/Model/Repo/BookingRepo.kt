package com.prabhanshu.roomify.Model.Repo

import com.prabhanshu.roomify.Model.Data.BookingResponse
import com.prabhanshu.roomify.Model.Network.ApiService
import com.prabhanshu.roomify.Model.Network.Request.BookingRequest
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun createBooking(token: String, request: BookingRequest): BookingResponse {
        return apiService.createBooking(request, "Bearer $token")
    }

    suspend fun getMyBookings(token: String): List<BookingResponse> {
        return apiService.getMyBookings("Bearer $token")
    }

    suspend fun deleteBooking(token: String, bookingId: String): Boolean {
        apiService.deleteBooking(bookingId, "Bearer $token")
        return true
    }
}