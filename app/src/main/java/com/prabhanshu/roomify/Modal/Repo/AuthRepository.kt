package com.prabhanshu.roomify.Modal.Repo

import com.prabhanshu.roomify.Modal.API.AuthApiService
import com.prabhanshu.roomify.Modal.AuthResponse
import com.prabhanshu.roomify.Modal.LoginRequest
import com.prabhanshu.roomify.Modal.RegisterRequest
import com.prabhanshu.roomify.Modal.User
import com.prabhanshu.roomify.locall.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
@Singleton
class AuthRepository @Inject constructor(
    private val apiService: AuthApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun login(email: String, password: String): Resource<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                preferencesManager.saveAuthData(authResponse.token, authResponse.user)
                Resource.Success(authResponse)
            } else {
                Resource.Error("Invalid credentials")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun register(name: String, email: String, password: String, isHost: Boolean): Resource<String> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password, isHost))
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.message)
            } else {
                Resource.Error("Registration failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    fun logout() {
        preferencesManager.clearAuthData()
    }

    fun isLoggedIn(): Boolean = preferencesManager.isLoggedIn()

    fun getCurrentUser(): User? = preferencesManager.getCurrentUser()
}