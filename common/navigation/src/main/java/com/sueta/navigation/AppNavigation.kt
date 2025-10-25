package com.sueta.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sueta.navigation.Navigation.Args.CHAT_ID
import com.sueta.navigation.Navigation.Args.IS_NEW_USER
import com.sueta.navigation.Navigation.Args.USERNAME
import com.sueta.network.presentation.TokenViewModel
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
            route = Navigation.Routes.ONBOARDING,
            arguments = listOf(
                navArgument(name = IS_NEW_USER) {
                    type = NavType.BoolType
                })
        ) { backStackEntry ->
            val isNewUser = backStackEntry.arguments?.getBoolean(IS_NEW_USER) ?: false

            OnboardingScreenDestination(isNewUser,navController)
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

//        composable(
//            Navigation.Routes.CHAT,
//            arguments = listOf(
//                navArgument(name = CHAT_ID) {
//                    type = NavType.IntType
//                })
//        ) { backStackEntry ->
//            val chatId = backStackEntry.arguments?.getInt(CHAT_ID) ?: 0
//
//            ChatScreenDestination(chatId, navController)
//        }
    }
}

object Navigation {

    object Args {
        const val USERNAME = "username"
        const val CHAT_ID = "chat_id"
        const val IS_NEW_USER = "is_new_user"
    }

    object Routes {
        const val MAIN = "main"
        const val AUTH = "auth"
        const val SELF_PROFILE = "profile"
        const val SPLASH = "splash"
        const val PROFILE = "$SELF_PROFILE/{$USERNAME}"
        const val ONBOARDING = "onboarding/{$IS_NEW_USER}"
        const val CHAT = "chat/{$CHAT_ID}"
    }


}

