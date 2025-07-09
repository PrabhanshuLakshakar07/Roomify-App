package com.prabhanshu.roomify.Presentation.Navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation{
    @Serializable
    object MainHomeScreen:SubNavigation()

    @Serializable
    object AuthScreen:SubNavigation()

}

sealed class Route {

    @Serializable
    object LoginScreen

    @Serializable
    object RegisterScreen

    @Serializable
    object HomeScreen

    @Serializable
    object BookScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object CreateListingScreen

    @Serializable
    data class BookingScreen(val listingId: String) : Route()






}