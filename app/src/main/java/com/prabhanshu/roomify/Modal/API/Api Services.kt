package com.prabhanshu.roomify.Modal.API

import com.prabhanshu.roomify.Modal.AuthResponse
import com.prabhanshu.roomify.Modal.CreateListingRequest
import com.prabhanshu.roomify.Modal.Listing
import com.prabhanshu.roomify.Modal.LoginRequest
import com.prabhanshu.roomify.Modal.MessageResponse
import com.prabhanshu.roomify.Modal.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

 /////////////listing/////


    @GET("listings")
    suspend fun getListings(
        @Query("location") location: String? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
    ): Response<List<Listing>>

    @GET("listings/{id}")
    suspend fun getListingById(@Path("id") id: String): Response<Listing>

    @POST("listings")
    suspend fun createListing(
        @Header("Authorization") token: String,
        @Body listing: CreateListingRequest
    ): Response<Listing>

    @PUT("listings/{id}")
    suspend fun updateListing(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body listing: CreateListingRequest
    ): Response<Listing>

    @DELETE("listings/{id}")
    suspend fun deleteListing(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Map<String, String>>





}