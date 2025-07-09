package com.prabhanshu.roomify.Model.Repo

import com.prabhanshu.roomify.Model.Data.CreateListingRequest
import com.prabhanshu.roomify.Model.Data.Listing
import com.prabhanshu.roomify.Model.Data.UpdateListingRequest
import com.prabhanshu.roomify.Model.Network.ApiService
import javax.inject.Inject

class ListingRepository @Inject constructor(
    private val api: ApiService
) {


    suspend fun getAllListings() = api.getAllListings()
    suspend fun getListing(id: String) = api.getListingById(id)
    suspend fun getMyListings(token: String) = api.getMyListings("Bearer $token")
    suspend fun create(token: String, body: CreateListingRequest) = api.createListing("Bearer $token", body)
    suspend fun update(token: String, id: String, body: UpdateListingRequest) = api.updateListing("Bearer $token", id, body)
    suspend fun delete(token: String, id: String) = api.deleteListing("Bearer $token", id)
}





