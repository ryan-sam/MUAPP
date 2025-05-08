package com.example.muapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.muapp.presentation.screens.Login.LoginForm
import com.example.muapp.presentation.screens.dashboard.DashboardScreen

// we will define navController : this is used to navigate to diff composable
@Composable
fun MUAPPNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "Dashboard") {
            composable("Dashboard"){
                DashboardScreen(
                    // properties for the composable
                )

            }

    // here will define the addtoDo composable
}

        }


