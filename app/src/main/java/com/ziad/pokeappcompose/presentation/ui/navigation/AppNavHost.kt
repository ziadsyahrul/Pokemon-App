package com.ziad.pokeappcompose.presentation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ziad.pokeappcompose.presentation.ui.detail.DetailRoute
import com.ziad.pokeappcompose.presentation.ui.login.LoginRoute
import com.ziad.pokeappcompose.presentation.ui.main.MainScreen
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
                        rootNavController = navController,
                        onNavigateToDetail  = { url ->
                            navController.navigate(ScreenNavigation.Detail.create(url))
                        }
                    )
                }

                composable(
                    route = ScreenNavigation.Detail.route,
                    arguments = listOf(
                        navArgument("url") { type = NavType.StringType }
                    )
                ) { backStack ->
                    val encodedUrl = backStack.arguments?.getString("url") ?: ""
                    val decodedUrl = Uri.decode(encodedUrl)

                    DetailRoute(
                        url = decodedUrl,
                        onBack = { navController.popBackStack() }
                    )
                }
            }

        }
    }

}