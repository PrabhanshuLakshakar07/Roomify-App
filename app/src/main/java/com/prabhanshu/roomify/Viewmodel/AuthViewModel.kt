package com.prabhanshu.roomify.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhanshu.roomify.Model.Repo.AuthRepository
import com.prabhanshu.roomify.Presentation.State.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(AuthUiState())
    val uiState: LiveData<AuthUiState> = _uiState

    fun register(name: String, email: String, password: String,isHost: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isLoading = true, errorMessage = null)

            authRepository.register(name, email, password,isHost).fold(
                onSuccess = { user ->
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        isSuccess = true,
                        user = user
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            )
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isLoading = true, errorMessage = null)

            authRepository.login(email, password).fold(
                onSuccess = { user ->
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        isSuccess = true,
                        user = user
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value?.copy(errorMessage = null)
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
