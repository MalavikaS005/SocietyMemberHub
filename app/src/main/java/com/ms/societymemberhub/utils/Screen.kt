package com.ms.societymemberhub.utils

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
}
