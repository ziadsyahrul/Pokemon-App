package com.ziad.pokeappcompose.presentation.ui.navigation

sealed class GraphNavigation(val route: String) {

    object Root: GraphNavigation("root")
    object Auth: GraphNavigation("auth")
    object Home: GraphNavigation("home")
}