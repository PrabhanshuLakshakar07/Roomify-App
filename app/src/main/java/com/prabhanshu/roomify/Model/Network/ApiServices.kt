package com.prabhanshu.roomify.Model.Network

import com.prabhanshu.roomify.Model.Data.ApiResponse
import com.prabhanshu.roomify.Model.Data.AuthResponse
import com.prabhanshu.roomify.Model.Data.BookingResponse
import com.prabhanshu.roomify.Model.Data.CreateListingRequest
import com.prabhanshu.roomify.Model.Data.Listing
import com.prabhanshu.roomify.Model.Data.Profile
import com.prabhanshu.roomify.Model.Data.UpdateListingRequest
import com.prabhanshu.roomify.Model.Network.Request.BookingRequest

import com.prabhanshu.roomify.Model.Network.Request.LoginRequest
import com.prabhanshu.roomify.Model.Network.Request.ProfileRequest
import com.prabhanshu.roomify.Model.Network.Request.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    ////booking////
    @POST("bookings")
    suspend fun createBooking(
        @Body bookingRequest: BookingRequest,
        @Header("Authorization") token: String
    ): BookingResponse

    @GET("bookings/my-bookings")
    suspend fun getMyBookings(
        @Header("Authorization") token: String
    ): List<BookingResponse>

    @DELETE("bookings/{id}")
    suspend fun deleteBooking(
        @Path("id") bookingId: String,
        @Header("Authorization") token: String
    ): ResponseBody





    ///////listing/////

    @GET("/api/listings")
    suspend fun getAllListings(
        @Query("location") location: String? = null,
        @Query("minPrice") minPrice: Int? = null,
        @Query("maxPrice") maxPrice: Int? = null
    ): List<Listing>

    @GET("listings/{id}")
    suspend fun getListingById(@Path("id") id: String): Listing

    @GET("listings/my-listings")
    suspend fun getMyListings(@Header("Authorization") token: String): List<Listing>

    @POST("listings")
    suspend fun createListing(
        @Header("Authorization") token: String,
        @Body body: CreateListingRequest
    ): Listing

    @PUT("listings/{id}")
    suspend fun updateListing(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body body: UpdateListingRequest
    ): Listing

    @DELETE("listings/{id}")
    suspend fun deleteListing(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ResponseBody
    ///////profile

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<Profile>

    @POST("profile")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Body profile: ProfileRequest
    ): Response<Profile>

    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: ProfileRequest
    ): Response<Profile>

    @DELETE("profile")
    suspend fun deleteProfile(
        @Header("Authorization") token: String
    ): Response<ApiResponse>
}
