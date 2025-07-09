package com.prabhanshu.roomify.Presentation.Navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Presentation.Auth.LoginScreen
import com.prabhanshu.roomify.Presentation.Auth.RegisterScreen
import com.prabhanshu.roomify.Presentation.Main.BookScreen
import com.prabhanshu.roomify.Presentation.Main.BookingScreen
import com.prabhanshu.roomify.Presentation.Main.HomeScreen
import com.prabhanshu.roomify.Presentation.Main.ProfileScreen
import com.prabhanshu.roomify.Presentation.Navigation.Route.CreateListingScreen
import com.prabhanshu.roomify.Presentation.Navigation.Route.RegisterScreen
import com.prabhanshu.roomify.Viewmodel.AuthViewModel


@Composable
fun App(navController: NavHostController = rememberNavController()


) {

    val context = LocalContext.current
    val preferenceManager = remember { PreferencesManager(context) } // ✅ Create instance here

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val backStackEntry = navController.currentBackStackEntryAsState()


    // Update selectedItem based on backStackEntry
    backStackEntry.value?.let { entry ->
        selectedItemIndex = when (entry.destination.route) {
            Route.HomeScreen::class.qualifiedName -> 0
            Route.BookScreen::class.qualifiedName -> 1
            Route.ProfileScreen::class.qualifiedName -> 2


            else -> 0
        }
    }

    val isBottomBarVisible = backStackEntry.value?.destination?.route in listOf(
        Route.HomeScreen::class.qualifiedName,
        Route.BookScreen::class.qualifiedName,
        Route.ProfileScreen::class.qualifiedName,

    )
    // Determine start destination based on authentication status

    val startDestination = if (PreferencesManager(context).getAuthToken() != null) {
        SubNavigation.MainHomeScreen
    } else {
        SubNavigation.AuthScreen
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    items = bottomNavigationItems,
                    selectedItemIndex = selectedItemIndex,
                    onItemClick = { index ->
                        selectedItemIndex = index

                        when (selectedItemIndex) {
                            0 -> {
                                navController.navigate(Route.HomeScreen) {
                                    launchSingleTop = true
                                    popUpTo(Route.HomeScreen) {
                                        inclusive = true
                                    }
                                }
                            }

                            1 -> {
                                navController.navigate(Route.BookScreen) {
                                    launchSingleTop = true
                                    popUpTo(Route.BookScreen) {
                                        inclusive = true
                                    }
                                }
                            }



                            2 -> {
                                navController.navigate(Route.ProfileScreen) {
                                    launchSingleTop = true
                                    popUpTo(Route.ProfileScreen) {
                                        inclusive = true

                                    }}}

                        }
                    }
                )
            }
        }){ innerPadding->

//
//        val startScreen = if (preferenceManager.getLoginUserId() == null) {
//            SubNavigation.AuthScreen
//        } else {
//            SubNavigation.MainHomeScreen
//        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ){

            NavHost(navController=navController, startDestination = startDestination) {

                navigation<SubNavigation.AuthScreen>(startDestination = Route.LoginScreen) {
                    composable<Route.LoginScreen> {
                        val authViewModel: AuthViewModel =hiltViewModel()
                        LoginScreen(
                            viewModel = authViewModel,
                            onNavigateToRegister = { navController.navigate(Route.RegisterScreen) },
                            onNavigateToProfile = {
                                Log.d("Navigation", "onNavigateToProfile called - about to navigate")
                                navController.navigate(Route.HomeScreen) {
                                    popUpTo(SubNavigation.AuthScreen) { inclusive = true }
                                }
                                Log.d("Navigation", "Navigation to HomeScreen completed")
                            }

                        )

                    }
                    composable<Route.RegisterScreen> {
                        val authViewModel: AuthViewModel =hiltViewModel()
                        RegisterScreen(
                            viewModel = authViewModel,
                            onNavigateToLogin = { navController.navigate(Route.LoginScreen) },
                            onNavigateToProfile = { navController.navigate(Route.HomeScreen) }
                        )


                    }

                }
                navigation<SubNavigation.MainHomeScreen>(startDestination = Route.HomeScreen) {


                    composable<Route.HomeScreen> {
                        Log.d("Navigation", "Navigating to HomeScreen")
                        HomeScreen(

                            navController = navController


                        )
                    }
                    composable<Route.BookScreen>{
                        BookScreen()
                    }


                    composable<Route.ProfileScreen> {
                        ProfileScreen(


                        )
                    }
                    composable<Route.BookingScreen> {
                        val listingId = it.arguments?.getString("listingId") ?: ""
                        BookingScreen(
                            listingId = listingId,
                            navController = navController
                        )

                    }




                    composable<Route.CreateListingScreen> {
                        com.prabhanshu.roomify.Presentation.Main.CreateListingScreen(
                            viewModel = hiltViewModel(),
                            navController = navController
                        )

                      }



                }


            }


        }
    }

}
