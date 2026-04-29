package com.ziad.pokeappcompose.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ziad.pokeappcompose.presentation.ui.home.HomeRoute
import com.ziad.pokeappcompose.presentation.ui.navigation.ScreenNavigation
import com.ziad.pokeappcompose.presentation.ui.profile.ProfileRoute

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onNavigateToDetail: (String) -> Unit
) {

    val innerNavController = rememberNavController()
    val items = listOf(
        ScreenNavigation.Home,
        ScreenNavigation.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {

                val currentRoute =
                    innerNavController.currentBackStackEntryAsState()
                        .value?.destination?.route

                items.forEach { screen ->

                    val icon = when (screen) {
                        ScreenNavigation.Home -> Icons.Default.Home
                        ScreenNavigation.Profile -> Icons.Default.Person
                        else -> Icons.Default.Home
                    }

                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            innerNavController.navigate(screen.route) {
                                popUpTo(innerNavController.graph.startDestinationId)
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, null) },
                        label = { Text(screen.route) }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = innerNavController,
            startDestination = ScreenNavigation.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(ScreenNavigation.Home.route) {
                HomeRoute(
                    onNavigateToDetail = { url ->
                        onNavigateToDetail(url)
                    }
                )
            }

            composable(ScreenNavigation.Profile.route) {
                ProfileRoute(
                    rootNavController = rootNavController
                )
            }
        }
    }
}