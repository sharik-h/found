package com.example.found.LoginNavigation

sealed class Screen(val route: String) {
    object splash: Screen(route = "Splash")
    object search: Screen(route = "search")
    object loginOptions: Screen(route = "loginOptions")
    object EnterUserDetails: Screen(route = "EnterUserDetails")
    object AuthOTP: Screen(route = "AuthOTP")
}