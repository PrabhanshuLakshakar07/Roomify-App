package com.prabhanshu.roomify.Model.Repo

import android.util.Log
import com.prabhanshu.roomify.Model.Data.User
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Model.Network.ApiService
import com.prabhanshu.roomify.Model.Network.Request.LoginRequest
import com.prabhanshu.roomify.Model.Network.Request.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun register(name: String, email: String, password: String,isHost: Boolean): Result<User> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password, isHost))
            Log.d("REPOSITORY", "Sending isHost: $isHost")
            preferencesManager.saveAuthToken(response.token)
            preferencesManager.saveUserData(response.user)
            Result.success(response.user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            Log.d("AuthRepository", "Making login API call")

            val response = apiService.login(LoginRequest(email, password))

            preferencesManager.saveAuthToken(response.token)
            preferencesManager.saveUserData(response.user)
            Result.success(response.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun isLoggedIn(): Boolean = preferencesManager.isLoggedIn()
}
