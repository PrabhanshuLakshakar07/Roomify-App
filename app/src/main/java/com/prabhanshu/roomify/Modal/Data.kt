package com.prabhanshu.roomify.Modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: String,
    val name: String,
    val email: String,
    val isHost: Boolean
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val isHost: Boolean
)

data class AuthResponse(
    val token: String,
    val user: User
)

data class MessageResponse(
    val message: String
)

///////////listing//////
//data class Listing(
//    val _id: String,
//    val title: String,
//    val description: String,
//    val price: Int,
//    val location: String,
//    val imageUrl: String,
//)
data class Listing(
    val _id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val hostId: String = "",
    val bookedDates: List<BookedDate> = emptyList()
)

data class BookedDate(
    val start: String,
    val end: String
)

data class CreateListingRequest(
    val title: String,
    val description: String,
    val location: String,
    val price: Double,
    val images: List<String> = emptyList()
)
data class HomeUiState(
    val listings: List<Listing> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAddingListing: Boolean = false
)
data class ImageUploadResponse(
    val success: Boolean,
    val imageUrl: String? = null,
    val message: String? = null
)