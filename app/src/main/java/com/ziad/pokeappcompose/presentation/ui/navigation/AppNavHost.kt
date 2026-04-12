package com.ziad.pokeappcompose.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ziad.pokeappcompose.presentation.ui.MainScreen
import com.ziad.pokeappcompose.presentation.ui.home.HomeScreen
import com.ziad.pokeappcompose.presentation.ui.login.LoginRoute
import com.ziad.pokeappcompose.presentation.ui.register.RegisterRoute
import com.ziad.pokeappcompose.presentation.viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val isLogin by authViewModel.isLogin.collectAsState()

    NavHost(
        navController = navController,
        startDestination = GraphNavigation.Root.route
    ) {
        navigation(
            route = GraphNavigation.Root.route,
            startDestination = if (isLogin)
                GraphNavigation.Home.route
            else
                GraphNavigation.Auth.route
        ) {

            navigation(
                route = GraphNavigation.Auth.route,
                startDestination = ScreenNavigation.Login.route
            ) {
                composable(ScreenNavigation.Login.route) {
                    LoginRoute(
                        onLoginSuccess = {
                            navController.navigate(GraphNavigation.Home.route) {
                                popUpTo(GraphNavigation.Auth.route) { inclusive = true }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate(ScreenNavigation.Register.route)
                        }
                    )
                }

                composable(ScreenNavigation.Register.route) {
                    RegisterRoute(
                        onRegisterSuccess = {
                            navController.popBackStack()
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            navigation(
                route = GraphNavigation.Home.route,
                startDestination = "home_tabs"
            ) {
                composable("home_tabs") {
                    MainScreen(
                        rootNavController = navController
                    )
                }
            }

        }
    }

}