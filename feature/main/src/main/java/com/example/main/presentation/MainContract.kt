package com.example.main.presentation

import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState
import com.example.main.presentation.ui.navigation.MainNavigation

class MainContract {
    enum class Item(val value: String, val route: String) {
        MAIN("Главная", MainNavigation.Routes.CURRENCY),
        NEWS("Новости", MainNavigation.Routes.CURRENCY),
        DIRECT("Чаты", MainNavigation.Routes.CHATS);

        companion object {
            fun fromRoute(route: String?): Item? =
                entries.find { it.route == route }
        }

    }


    sealed class Event : ViewEvent {

//        object MainItemSelected : Event()
//        object NewsItemSelected : Event()
//        object DirectItemSelected : Event()

        data class onItemSelected(val item: Item) : Event()
        data class OnRouteChanged(val route: String?) : Event()
        object ProfileButtonCLicked : Event()
    }

    data class State(
        val item: Item = Item.MAIN,
        val error: String? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect {


        sealed class Navigation : Effect() {
            object toSelfProfile : Navigation()

        }
    }

}