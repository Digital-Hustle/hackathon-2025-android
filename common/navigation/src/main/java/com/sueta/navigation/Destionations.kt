package com.sueta.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sueta.auth.presentation.AuthContract
import com.sueta.auth.presentation.AuthViewModel
import com.sueta.auth.presentation.screens.AuthScreen
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.MainViewModel
import com.sueta.main.presentation.ui.screens.MainScreen
import com.sueta.profile.presentation.ProfileContract
import com.sueta.profile.presentation.ProfileViewModel
import com.sueta.profile.presentation.ui.screens.ProfileScreen


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

//@Composable
//fun ChatScreenDestination(chatId: Int, navController: NavController) {
//
//    val viewModel: ChatViewModel = hiltViewModel()
//
////    viewModel.setChatID(chatId)
//    DisposableEffect(chatId) {
////        viewModel.subscribeToMessages(chatId)
//
//        onDispose {
//            viewModel.closedChat()
//        }
//    }
//    LaunchedEffect(chatId) {
//        viewModel.setChatID(chatId)
//    }
//    ChatScreen(
//        state = viewModel.viewState.collectAsState().value,
//        effectFlow = viewModel.effect,
//        onEventSent = { event -> viewModel.setEvent(event) },
//        onNavigationRequested = { navigationEffect ->
////            if (navigationEffect is CoinContract.Effect.Navigation.ToMain) {
////                navController.navigateToMain()
////            }
//
//        }
//    )
//}


fun NavController.navigateToMain() {
    navigate(route = Navigation.Routes.MAIN)
}

fun NavController.navigateToSelfProfile() {
    navigate(route = Navigation.Routes.SELF_PROFILE)
}

