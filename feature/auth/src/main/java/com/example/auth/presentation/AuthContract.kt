package com.example.auth.presentation

import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState

class AuthContract {

    sealed class Event : ViewEvent {

        data class PasswordChanged(val query: String) : Event()
        data class PasswordConfirmationChanged(val query: String) : Event()
        data class LoginChanged(val query: String) : Event()
        data object RegistrationButtonClicked : Event()

        data object LoginButtonClicked : Event()

        data class ChangeAuthTypeButtonClicked(val select: Boolean) : Event()
        data object LoginWithGoogleButtonClicked : Event()
    }

    data class State(
        val isRegistration: Boolean = false,
        val password: String = "",
        val passwordConfirmation: String = "",
        val login: String = "",
//        val loginResponse: LoginResponse? = null,
//        val registrationResponse: RegistrationResponse? = null,
        val error: String? = null,
        val isLoading: Boolean = false,
        val passwordConfirmationIsVisible: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class LoginWasLoaded(val token: String, val refreshToken: String) : Effect()
        object RegistrationWasLoaded : Effect()


        sealed class Navigation : Effect() {
            object toMain : Navigation()
        }
    }

}