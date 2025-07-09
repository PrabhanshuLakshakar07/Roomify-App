package com.prabhanshu.roomify.Model.Repo

import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Model.Network.ApiService
import com.prabhanshu.roomify.Model.Network.Request.ProfileRequest
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ApiService,
    private val prefs: PreferencesManager
) {
    private fun authHeader() = "Bearer ${prefs.getAuthToken()}"

    suspend fun getProfile() = api.getProfile(authHeader())
    suspend fun createProfile(data: ProfileRequest) = api.createProfile(authHeader(), data)
    suspend fun updateProfile(data: ProfileRequest) = api.updateProfile(authHeader(), data)
    suspend fun deleteProfile() = api.deleteProfile(authHeader())
}
