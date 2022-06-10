package com.example.found.LoginNavigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.found.LoginPages.CreateAccount.EnterUserDetails
import com.example.found.LoginPages.LoginWith.LoginWithMob
import com.example.found.LoginPages.login.LoginOptions
import com.example.found.SearchPage.LocationListPage
import com.example.found.SplashScreen.Splash
import com.example.found.data.firestoreViewModel

@Composable
fun NavGraph(navHostController: NavHostController, viewModel: firestoreViewModel) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.splash.route
    ){
        composable(route = Screen.splash.route){
            Splash(navHostController = navHostController)
        }
        composable(route = Screen.search.route){
            LocationListPage(viewModel = viewModel)
        }
        composable(route = Screen.loginOptions.route){
            LoginOptions(navHostController = navHostController  )
        }
        composable(route = Screen.enterUserDetails.route) {
            EnterUserDetails(navHostController = navHostController)
        }
        composable(route = Screen.loginWithMobile.route) {
            LoginWithMob(navHostController = navHostController)
        }
    }
}