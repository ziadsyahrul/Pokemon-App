package com.ziad.pokeappcompose.presentation.ui.navigation

import android.net.Uri

sealed class ScreenNavigation(val route: String) {
    object Login : ScreenNavigation("login")
    object Register : ScreenNavigation("register")

    object Home : ScreenNavigation("home")
    object Profile : ScreenNavigation("profile")

    object Detail : ScreenNavigation("detail/{url}") {
        fun create(url: String): String {
            return "detail/${Uri.encode(url)}"
        }
    }
}