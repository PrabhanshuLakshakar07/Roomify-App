package com.prabhanshu.roomify.Presentation.State

import com.prabhanshu.roomify.Model.Data.Listing
import com.prabhanshu.roomify.Model.Data.User


// ui/state/AuthUiState.kt
data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val isUpdateSuccess: Boolean = false
)
data class ListingsUiState(
    val isLoading: Boolean = false,
    val listings: List<Listing> = emptyList(),
    val error: String? = null,
    val isCreating: Boolean = false,
    val createSuccess: Boolean = false
)