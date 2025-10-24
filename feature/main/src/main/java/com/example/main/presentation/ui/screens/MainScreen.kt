package com.example.main.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.main.presentation.MainContract
import com.example.main.presentation.ui.components.MainTopAppBar
import com.example.main.presentation.ui.navigation.MainNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreen(
    state: MainContract.State,
    effectFlow: Flow<MainContract.Effect>?,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
    rootNavController: NavController
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    LaunchedEffect(currentRoute) {
       onEventSent(MainContract.Event.OnRouteChanged(currentRoute))
    }


    LaunchedEffect(Unit) {


        val currentItem = MainContract.Item.entries.find { it.route == currentRoute }
        currentItem?.let {
            onEventSent(MainContract.Event.onItemSelected(it))
        }


        effectFlow?.onEach { effect ->
            when (effect) {
                MainContract.Effect.Navigation.toSelfProfile ->
                    onNavigationRequested(MainContract.Effect.Navigation.toSelfProfile)
            }
        }?.collect()
    }




    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        MainTopAppBar(state.item.value, rootNavController)

        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            MainNavigation(navController, rootNavController)
        }
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.onTertiary,
        ) {
            MainContract.Item.entries.forEach { item ->
                val interactionSource = remember { MutableInteractionSource() }
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector =
                            when (item) {
                                MainContract.Item.MAIN -> Icons.Filled.Home
                                MainContract.Item.NEWS -> Icons.Filled.Newspaper
                                MainContract.Item.DIRECT -> Icons.AutoMirrored.Filled.Message
                            }, contentDescription = item.value,
                            modifier = Modifier.fillMaxSize(0.22f)
                        )
                    },
                    onClick = {
                        onEventSent(MainContract.Event.onItemSelected(item))
                        navController.navigate(item.route)
                    },
                    selected = state.item == item,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    ),
                    interactionSource = interactionSource,
                    modifier = Modifier.indication(
                        interactionSource = interactionSource,
                        indication = null
                    ),
                )
            }
        }

//        when (state.item) {
//
//            MainContract.Item.MAIN ->{
//                Log.d("mLogTEST","TYT")
//
//                navController.navigate(MainNavigation.Routes.CURRENCY)}
//            MainContract.Item.NEWS ->navController.navigate(MainNavigation.Routes.CURRENCY)
//            MainContract.Item.DIRECT ->  navController.navigate(MainNavigation.Routes.CHATS)
//        }

    }
}

