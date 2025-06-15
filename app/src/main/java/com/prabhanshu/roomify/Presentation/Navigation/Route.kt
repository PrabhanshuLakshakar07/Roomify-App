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
    object BookingScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object ListingDetailScreen

    @Serializable
    object CreateListingScreen

    @Serializable
    object EditListingScreen



}