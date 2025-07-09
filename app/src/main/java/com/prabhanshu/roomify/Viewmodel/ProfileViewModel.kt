package com.prabhanshu.roomify.Viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Model.Data.Profile

import com.prabhanshu.roomify.Model.Network.Request.ProfileRequest
import com.prabhanshu.roomify.Model.Repo.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository
) : ViewModel() {

    var profile by mutableStateOf<Profile?>(null)
        private set

    var message by mutableStateOf("")
        private set

    var error by mutableStateOf("")
        private set

    fun fetchProfile() = viewModelScope.launch {
        try {
            val res = repo.getProfile()
            if (res.isSuccessful) {
                profile = res.body()
            } else {
                error = res.message()
            }
        } catch (e: Exception) {
            error = e.message ?: "Unknown error"
        }
    }

    fun createProfile(request: ProfileRequest) = viewModelScope.launch {
        try {
            val res = repo.createProfile(request)
            if (res.isSuccessful) {
                profile = res.body()
                message = "Profile created"
            } else {
                error = res.message()
            }
        } catch (e: Exception) {
            error = e.message ?: "Error creating"
        }
    }

    fun updateProfile(request: ProfileRequest) = viewModelScope.launch {
        try {
            val res = repo.updateProfile(request)
            if (res.isSuccessful) {
                profile = res.body()
                message = "Profile updated"
            } else {
                error = res.message()
            }
        } catch (e: Exception) {
            error = e.message ?: "Error updating"
        }
    }

    fun deleteProfile() = viewModelScope.launch {
        try {
            val res = repo.deleteProfile()
            if (res.isSuccessful) {
                message = res.body()?.message ?: "Deleted"
                profile = null
            } else {
                error = res.message()
            }
        } catch (e: Exception) {
            error = e.message ?: "Error deleting"
        }
    }
}
