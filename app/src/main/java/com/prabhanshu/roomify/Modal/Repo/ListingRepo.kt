package com.prabhanshu.roomify.Modal.Repo

import com.prabhanshu.roomify.Modal.API.AuthApiService
import com.prabhanshu.roomify.Modal.CreateListingRequest
import com.prabhanshu.roomify.Modal.Listing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingsRepository @Inject constructor(
    private val api: AuthApiService
) {
    fun getListings(
        location: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ): Flow<Resource<List<Listing>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getListings(location, minPrice, maxPrice)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Failed to fetch listings"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun getListingById(id: String): Flow<Resource<Listing>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getListingById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Failed to fetch listing details"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun createListing(
        token: String,
        listing: CreateListingRequest
    ): Flow<Resource<Listing>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.createListing("Bearer $token", listing)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Failed to create listing"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }




}