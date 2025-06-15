package com.prabhanshu.roomify.locall

import android.content.Context
import android.content.SharedPreferences
import com.prabhanshu.roomify.Modal.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "auth_token"
        private const val USER_ID_KEY = "user_id"
        private const val USER_NAME_KEY = "user_name"
        private const val USER_EMAIL_KEY = "user_email"
        private const val IS_HOST_KEY = "is_host"
        private const val IS_LOGGED_IN_KEY = "is_logged_in"
    }

    fun saveAuthData(token: String, user: User) {
        prefs.edit().apply {
            putString(TOKEN_KEY, token)
            putString(USER_ID_KEY, user.id)
            putString(USER_NAME_KEY, user.name)
            putString(USER_EMAIL_KEY, user.email)
            putBoolean(IS_HOST_KEY, user.isHost)
            putBoolean(IS_LOGGED_IN_KEY, true)
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)

    fun getCurrentUser(): User? {
        return if (isLoggedIn()) {
            User(
                id = prefs.getString(USER_ID_KEY, "") ?: "",
                name = prefs.getString(USER_NAME_KEY, "") ?: "",
                email = prefs.getString(USER_EMAIL_KEY, "") ?: "",
                isHost = prefs.getBoolean(IS_HOST_KEY, false)
            )
        } else null
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN_KEY, false)

    fun clearAuthData() {
        prefs.edit().clear().apply()
    }
}