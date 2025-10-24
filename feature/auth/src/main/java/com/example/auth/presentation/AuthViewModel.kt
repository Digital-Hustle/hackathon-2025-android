package com.example.auth.presentation

import androidx.lifecycle.viewModelScope
import com.example.auth.domain.models.LoginRequest
import com.example.auth.domain.models.RegistrationRequest
import com.example.authtest.domain.repository.AuthRepository
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : BaseViewModel<AuthContract.Event, AuthContract.State, AuthContract.Effect>() {

    override fun setInitialState(): AuthContract.State = AuthContract.State()

    override fun handleEvents(event: AuthContract.Event) {
        when (event) {
            is AuthContract.Event.RegistrationButtonClicked ->
                registration()

            is AuthContract.Event.LoginButtonClicked ->
                login()


            AuthContract.Event.LoginWithGoogleButtonClicked -> loginWithGoogle()
            is AuthContract.Event.ChangeAuthTypeButtonClicked -> setState {
                copy(isRegistration = event.select, error = null)
            }

            is AuthContract.Event.LoginChanged -> setState {

                copy(login = event.query)
            }

            is AuthContract.Event.PasswordConfirmationChanged -> setState {

                copy(
                    passwordConfirmation = event.query
                )
            }

            is AuthContract.Event.PasswordChanged -> setState { copy(password = event.query) }
        }
    }


    private fun login() {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                repository.login(LoginRequest(viewState.value.login, viewState.value.password))
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        viewModelScope.launch {
                            setId(response.data.id)
                            setUserName(response.data.username)
                        }
//                        setState { copy(loginResponse = response.data, error = null) }
                        setEffect {
                            AuthContract.Effect.LoginWasLoaded(
                                response.data.token,
                                response.data.refreshToken
                            )
                        }
                        setEffect { AuthContract.Effect.Navigation.toMain }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
                }
            }
        )
    }


    private fun loginWithGoogle() {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                repository.loginWithGoogle()
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
//                        setState { copy(loginResponse = response.data, error = null) }
                        viewModelScope.launch {
                            setId(response.data.id)
                            setUserName(response.data.username)
                        }
                        setEffect {
                            AuthContract.Effect.LoginWasLoaded(
                                response.data.token,
                                response.data.refreshToken
                            )
                        }
                        setEffect { AuthContract.Effect.Navigation.toMain }

                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
                }
            }
        )
    }

    private fun registration() {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                repository.registration(
                    RegistrationRequest(
                        viewState.value.login,
                        viewState.value.password,
                        viewState.value.passwordConfirmation
                    )
                )
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {

//                        setState { copy(registrationResponse = response.data, error = null) }
                        setEffect { AuthContract.Effect.RegistrationWasLoaded }

                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
                }
            }
        )
    }

    private suspend fun setId(id: Int) {
        repository.setId(id)
    }

    private suspend fun setUserName(name: String) {
        repository.setUserName(name)
    }
}