package com.example.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.auth.presentation.AuthContract
import com.example.auth.presentation.AuthViewModel
import com.example.auth.presentation.screens.AuthScreen
import com.example.chat.presentation.ChatViewModel
import com.example.chat.presentation.ui.screen.ChatScreen
import com.example.coin.presentation.CoinContract
import com.example.coin.presentation.CoinViewModel
import com.example.coin.presentation.ui.screens.CoinScreen
import com.example.core.mLog
import com.example.main.presentation.MainContract
import com.example.main.presentation.MainViewModel
import com.example.main.presentation.ui.screens.MainScreen
import com.example.profile.presentation.ProfileContract
import com.example.profile.presentation.ProfileViewModel
import com.example.profile.presentation.ui.screens.ProfileScreen

//import org.koin.androidx.compose.getViewModel

@Composable
fun AuthScreenDestination(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()

    AuthScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is AuthContract.Effect.Navigation.toMain) {
                navController.navigateToMain()
            }
        }
    )
}


@Composable
fun MainScreenDestination(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    MainScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is MainContract.Effect.Navigation.toSelfProfile) {
                navController.navigateToSelfProfile()
            }

        },
        rootNavController = navController
    )

}

@Composable
fun SelfProfileDestination(navController: NavController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    ProfileScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ProfileContract.Effect.Navigation.ToMain) {
                navController.navigateToMain()
            }

        },
    )
}

@Composable
fun CoinScreenDestination(coinId: String, navController: NavController) {
    val viewModel: CoinViewModel = hiltViewModel()
    viewModel.setCoinID(coinId)
    CoinScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is CoinContract.Effect.Navigation.ToMain) {
                navController.navigateToMain()
            }

        }
    )
}

@Composable
fun ChatScreenDestination(chatId: Int, navController: NavController) {

    val viewModel: ChatViewModel = hiltViewModel()

//    viewModel.setChatID(chatId)
    DisposableEffect(chatId) {
//        viewModel.subscribeToMessages(chatId)

        onDispose {
            viewModel.closedChat()
        }
    }
    LaunchedEffect(chatId) {
        viewModel.setChatID(chatId)
    }
    ChatScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
//            if (navigationEffect is CoinContract.Effect.Navigation.ToMain) {
//                navController.navigateToMain()
//            }

        }
    )
}


fun NavController.navigateToMain() {
    navigate(route = Navigation.Routes.MAIN)
}

fun NavController.navigateToSelfProfile() {
    navigate(route = Navigation.Routes.SELF_PROFILE)
}

