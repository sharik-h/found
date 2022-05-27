package com.example.found.LoginNavigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.found.SearchPage.Searchpage
import com.example.found.SplashScreen.Splash

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.splash.route
    ){
        composable(route = Screen.splash.route){
            Splash(navHostController = navHostController)
        }
        composable(route = Screen.search.route){
            Searchpage()
        }
    }
}