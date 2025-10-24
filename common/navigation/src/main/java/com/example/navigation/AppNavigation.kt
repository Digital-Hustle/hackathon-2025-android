package com.example.navigation


import android.util.Log
import com.example.navigation.Navigation.Args.COIN_ID
import com.example.navigation.Navigation.Args.USERNAME
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.mLog
import com.example.navigation.Navigation.Args.CHAT_ID
import com.example.network.presentation.TokenViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(startDestination: String) {

    val navController = rememberNavController()
    val tokenViewModel: TokenViewModel = hiltViewModel()



    LaunchedEffect(Unit) {
        launch {
            tokenViewModel.token.collect { newToken ->
                if (newToken == null) {
                    navController.navigate(Navigation.Routes.AUTH) {
                        popUpTo(0)
                    }
                }
            }
        }
    }



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Navigation.Routes.SELF_PROFILE
        ) {
            SelfProfileDestination(navController)
        }

        composable(route = Navigation.Routes.SPLASH) {

        }
        composable(route = Navigation.Routes.MAIN) {
            MainScreenDestination(navController)
        }
        composable(route = Navigation.Routes.AUTH) {
            AuthScreenDestination(navController)

        }

        composable(
            Navigation.Routes.PROFILE,
            arguments = listOf(
                navArgument(name = USERNAME) {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")

        }

        composable(
            Navigation.Routes.COIN,
            arguments = listOf(
                navArgument(name = COIN_ID) {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString(COIN_ID) ?: "bitcoin"
            CoinScreenDestination(coinId, navController)
        }

        composable(
            Navigation.Routes.CHAT,
            arguments = listOf(
                navArgument(name = CHAT_ID) {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt(CHAT_ID) ?: 0

            ChatScreenDestination(chatId, navController)
        }
    }
}

object Navigation {

    object Args {
        const val USERNAME = "username"
        const val COIN_ID = "coin_id"
        const val CHAT_ID = "chat_id"
    }

    object Routes {
        const val MAIN = "main"
        const val AUTH = "auth"
        const val SELF_PROFILE = "profile"
        const val SPLASH = "splash"
        const val PROFILE = "$SELF_PROFILE/{$USERNAME}"
        const val COIN = "coin/{$COIN_ID}"
        const val CHAT = "chat/{$CHAT_ID}"
    }


}

