package com.example.main.presentation.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chats.presentation.ChatsContract
import com.example.chats.presentation.ChatsViewModel
import com.example.chats.presentation.ui.screen.ChatsScreen
import com.example.currency.presentation.CurrencyContract
import com.example.currency.presentation.CurrencyViewModel
import com.example.currency.presentation.ui.screen.CurrencyScreen
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun CurrencyScreenDestination(rootNavController: NavController) {
    val viewModel: CurrencyViewModel = hiltViewModel()

    CurrencyScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is CurrencyContract.Effect.Navigation.ToCoinDetails) {
                rootNavController.navigateToCoinDetails(navigationEffect.coinId)
            }
        }
    )

}

@Composable
fun ChatsScreenDestination(rootNavController: NavController) {
    val viewModel: ChatsViewModel = hiltViewModel()

    ChatsScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ChatsContract.Effect.Navigation.ToChat) {
                rootNavController.navigateToChat(navigationEffect.chatId)
            }
        }
    )
}


fun NavController.navigateToCoinDetails(coinId: String) {
    navigate(
        MainNavigation.Routes.COIN_DETAILS.replace(
            "{${MainNavigation.Args.COIN_ID}}", coinId
        )
    )
}

fun NavController.navigateToChat(chatId: Int) {
    navigate(
        MainNavigation.Routes.CHAT.replace(
            "{${MainNavigation.Args.CHAT_ID}}", chatId.toString()
        )
    )
}

