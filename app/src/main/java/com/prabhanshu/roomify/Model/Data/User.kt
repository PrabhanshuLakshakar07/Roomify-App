package com.prabhanshu.roomify.Model.Data

data class User(
    val id: String,
    val name: String,
    val email: String? = null,
    val isHost: Boolean
)
// models/AuthResponse.kt
data class AuthResponse(
    val token: String,
    val user: User

)
data class BookingResponse(
    val _id: String,
    val listingId: Listing, // You may define this model already
    val checkIn: String,
    val checkOut: String
)


data class AuthData(
    val user: User,
    val token: String
)

data class ValidationError(
    val msg: String,
    val param: String,
    val location: String
)

// models/UserProfileResponse.kt

data class UserProfileResponse(
    val status: String,
    val message: String?,
    val data: UserData?
)

data class UserData(
    val user: User,
    val profile: Profile // Add this if it's returned
)
data class Profile(
    val id: String,
    val user: String,
    val bio: String?,
    val phone: String?,
    val gender: String?,
    val dob: String?,
    val avatar: String?,
    val location: String?,
    val createdAt: String
)

data class ApiResponse(
    val message: String
)
data class Listing(
    val _id: String,
    val title: String,
    val description: String,
    val location: String,
    val price: Int,
    val images: List<String>,
    val amenities: List<String>,
    val hostId: String
)

data class CreateListingRequest(
    val title: String,
    val description: String,
    val location: String,
    val price: Int,
    val images: List<String>,
    val amenities: List<String>
)

data class UpdateListingRequest(
    val title: String?,
    val description: String?,
    val price: Int?,
    val location: String?,
    val images: List<String>?,
    val amenities: List<String>?
)
