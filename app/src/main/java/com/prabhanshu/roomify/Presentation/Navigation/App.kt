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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prabhanshu.roomify.Presentation.Auth.LoginScreen
import com.prabhanshu.roomify.Presentation.Auth.RegisterScreen
import com.prabhanshu.roomify.Presentation.Main.BookingScreen
import com.prabhanshu.roomify.Presentation.Main.CreateListingScreen
import com.prabhanshu.roomify.Presentation.Main.HomeScreen
import com.prabhanshu.roomify.Presentation.Main.ProfileScreen
import com.prabhanshu.roomify.Viewmodel.AuthViewModel
import com.prabhanshu.roomify.locall.PreferencesManager

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
            Route.BookingScreen::class.qualifiedName -> 1
            Route.ProfileScreen::class.qualifiedName -> 2

            else -> 0
        }
    }

    val isBottomBarVisible = backStackEntry.value?.destination?.route in listOf(
        Route.HomeScreen::class.qualifiedName,
        Route.BookingScreen::class.qualifiedName,
        Route.ProfileScreen::class.qualifiedName,
        Route.ProfileScreen::class.qualifiedName
    )
    // Determine start destination based on authentication status

    val startDestination = if (preferenceManager.getToken() != null) {
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
                                navController.navigate(Route.BookingScreen) {
                                    launchSingleTop = true
                                    popUpTo(Route.BookingScreen) {
                                        inclusive = true
                                    }
                                }
                            }



                            2 -> {
                                navController.navigate(Route.ProfileScreen) {
                                    launchSingleTop = true
                                    popUpTo(Route.ProfileScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }){ innerPadding->


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

                navigation<SubNavigation.AuthScreen>(startDestination = Route.RegisterScreen) {
                    composable<Route.LoginScreen> {
                        val authViewModel: AuthViewModel =hiltViewModel()
                    LoginScreen(

                        viewModel = authViewModel,
                        onLoginSuccess = {
                            navController.navigate(SubNavigation.MainHomeScreen) {
                                popUpTo(SubNavigation.AuthScreen) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate(Route.RegisterScreen)
                        }
                    )
                    }
                    composable<Route.RegisterScreen> {

                        val authViewModel: AuthViewModel =hiltViewModel()
                   RegisterScreen(
                       viewModel = authViewModel,
                       onRegisterSuccess = {
                           navController.navigate(Route.LoginScreen) {
                               popUpTo(Route.RegisterScreen) {
                                   inclusive = true
                               }
                           }
                       },
                       onNavigateToLogin = {
                           navController.navigate(Route.LoginScreen)
                       }
                   )
                    }

                }
                navigation<SubNavigation.MainHomeScreen>(startDestination = Route.HomeScreen) {


                    composable<Route.HomeScreen> {
                        Log.d("Navigation", "Navigating to HomeScreen")
                        HomeScreen(
                            navController,
                            onNavigateToDetail= {},
                            onNavigateToCreate={},


                        )
                    }
                    composable<Route.CreateListingScreen>{
                        CreateListingScreen(
                            token = preferenceManager.getToken() ?: "",

                            onListingCreated = {
                                navController.navigate(Route.HomeScreen)
                            }
                        )
                    }

                    composable<Route.BookingScreen> {
                        Log.d("Navigation", "Navigating to AppointmentScreen")
                        BookingScreen()
                    }


                    composable<Route.ProfileScreen> {
                        ProfileScreen(
                            onLogout = {
                                // Clear auth data and navigate to auth screen
                                preferenceManager.clearAuthData()
                                navController.navigate(SubNavigation.AuthScreen) {
                                    popUpTo(SubNavigation.MainHomeScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }



                }


            }


        }
    }

}

















