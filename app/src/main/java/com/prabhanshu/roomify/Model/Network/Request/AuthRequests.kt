package com.prabhanshu.roomify.Model.Network.Request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val isHost: Boolean
)

data class LoginRequest(
    val email: String,
    val password: String
)
data class UpdateProfileRequest(
    val name: String? = null,
    val bio: String? = null,
    val avatar: String? = null
)
data class ProfileRequest(
    val name: String,
    val email: String,
    val bio: String?,
    val isHost: Boolean
)
data class BookingRequest(
    val listingId: String,
    val checkIn: String,
    val checkOut: String
)
