package com.example.found.LoginNavigation

sealed class Screen(val route: String) {
    object splash: Screen(route = "Splash")
    object search: Screen(route = "search")
}