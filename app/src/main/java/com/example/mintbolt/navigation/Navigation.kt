package com.example.mintbolt.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mintbolt.screens.AnalyticsScreen
import com.example.mintbolt.screens.ChatBotScreen
import com.example.mintbolt.screens.HomeScreen
import com.example.mintbolt.screens.LoginScreen
import com.example.mintbolt.screens.ProfileScreen
import com.example.mintbolt.screens.ResetPass1
import com.example.mintbolt.screens.ResetPass2
import com.example.mintbolt.screens.ResetPass3
import com.example.mintbolt.screens.SignupScreen


@Composable
fun NavGraphBuilder(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(navController = navController)
        }
        composable("main") {
            HomeScreen(navController = navController)
        }

        composable("rp1") {
            ResetPass1(navController = navController)
        }

        composable("rp2") {
            ResetPass2(navController = navController)
        }

        composable("rp3") {
            ResetPass3(navController = navController)
        }

        composable("analytics")
        {
            AnalyticsScreen(navController = navController)
        }
        composable("profile")
        {
            ProfileScreen(navController = navController)
        }

        composable("chatbot"){
            ChatBotScreen(navController = navController)
        }
    }
}












