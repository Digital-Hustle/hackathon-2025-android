package com.example.main.presentation

import com.example.core.presentation.BaseViewModel

class MainViewModel :
    BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    override fun setInitialState(): MainContract.State = MainContract.State()

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
//            MainContract.Event.DirectItemSelected -> setState { copy(item = MainContract.Item.DIRECT) }
//            MainContract.Event.MainItemSelected -> setState { copy(item = MainContract.Item.MAIN) }
//            MainContract.Event.NewsItemSelected -> setState { copy(item = MainContract.Item.NEWS) }
            MainContract.Event.ProfileButtonCLicked -> setEffect { MainContract.Effect.Navigation.toSelfProfile }
            is MainContract.Event.onItemSelected -> setState { copy(item = event.item) }

            is MainContract.Event.OnRouteChanged -> {
                MainContract.Item.fromRoute(event.route)?.let { item ->
                    if (item != viewState.value.item) {
                        setState { copy(item = item) }
                    }
                }
            }
        }
    }


}