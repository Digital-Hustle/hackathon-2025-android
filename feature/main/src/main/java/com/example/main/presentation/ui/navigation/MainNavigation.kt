package com.example.main.presentation.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.mLog
import com.example.main.presentation.ui.navigation.MainNavigation.Args.CHAT_ID
import com.example.main.presentation.ui.navigation.MainNavigation.Args.COIN_ID


@Composable
fun MainNavigation(navController: NavHostController,rootNavController: NavController) {





    NavHost(
        navController = navController,
        startDestination = MainNavigation.Routes.CURRENCY
    ) {


        // Регистрируем экраны
        composable(MainNavigation.Routes.CURRENCY) {
            Log.d("mLogTEST","TYT")

                CurrencyScreenDestination(rootNavController)
        }
        composable(MainNavigation.Routes.NEWS) {
//            Log.d("mLogNav", "login")
        }
        composable(MainNavigation.Routes.CHATS){
            ChatsScreenDestination(rootNavController)

        }

        composable(MainNavigation.Routes.COIN_DETAILS) {
            rootNavController.navigate(MainNavigation.Routes.COIN_DETAILS)
        }

        composable(MainNavigation.Routes.CHAT) {
            mLog("NAVIGATE","TYT1")
            rootNavController.navigate(MainNavigation.Routes.CHAT)
        }


    }
}
object MainNavigation {

    object Args{
        const val COIN_ID = "coin_id"
        const val CHAT_ID = "chat_id"
    }

    object Routes {
        const val CURRENCY = "currency"
        const val NEWS = "news"
        const val CHATS = "chats"
        const val COIN_DETAILS = "coin/{$COIN_ID}"
        const val CHAT = "chat/{$CHAT_ID}"
    }

}


