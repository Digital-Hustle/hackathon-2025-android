package com.sueta.onboarding.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.sueta.core.domain.UserStorage
import com.sueta.core.presentation.BaseViewModel
import com.sueta.core.presentation.CoroutinesErrorHandler
import com.sueta.network.ApiResponse
import com.sueta.onboarding.data.mapper.toRequest
import com.sueta.onboarding.data.mapper.toStringSet
import com.sueta.onboarding.domain.repository.OnboardingRepository
import com.sueta.onboarding.presentation.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepository,
    private val userStorage: UserStorage
) : BaseViewModel<OnboardingContract.Event, OnboardingContract.State, OnboardingContract.Effect>() {
    override fun setInitialState(): OnboardingContract.State = OnboardingContract.State()

    fun setIsNewUser(value: Boolean) = setState {
        copy(isNewUser = value)
    }

    @SuppressLint("NewApi")
    override fun handleEvents(event: OnboardingContract.Event) {
        when (event) {
            is OnboardingContract.Event.OnNameChanged -> setState { copy(name = event.query) }
            is OnboardingContract.Event.OnSurnameChanged -> setState { copy(surname = event.query) }
            is OnboardingContract.Event.OnBirthDateChanged -> setState { copy(birthDate = event.query) }
            is OnboardingContract.Event.OnInterestChipClick -> setState {
                if (event.interest in viewState.value.interests) {
                    copy(interests = viewState.value.interests + event.interest)
                } else copy(interests = viewState.value.interests + event.interest)
            }

            OnboardingContract.Event.OnNextButtonClicked -> {


                when (viewState.value.currentStage) {
                    OnboardingStage.NAME -> {
                        if (viewState.value.name.isEmpty() || viewState.value.surname.isEmpty()) {
                            setEffect {
                                OnboardingContract.Effect.FieldsIsEmpty("Заполните имя и фамилию!")
                            }
                        } else {
                            setState {
                                copy(
                                    currentStage = OnboardingStage.BIRTH_DATE,
                                    label = "Когда вы родились?",
                                    description = "Мы учтем это при планировании ваших путешествий"
                                )

                            }
                        }
                    }

                    OnboardingStage.BIRTH_DATE -> {
                        if (viewState.value.birthDate == null) {
                            setEffect {
                                OnboardingContract.Effect.FieldsIsEmpty("Поле не может быть пустым!")
                            }
                        } else {
                            setState {
                                copy(
                                    currentStage = OnboardingStage.INTERESTS,
                                    label = "Ваши интересы",
                                    description = "Выберите темы, которые вам интересны"
                                )
                            }
                        }
                    }

                    OnboardingStage.INTERESTS -> {
                        if (viewState.value.interests.isEmpty()) {
                            setEffect { OnboardingContract.Effect.FieldsIsEmpty("Выберите хотя-бы один...") }
                        } else {
                            setProfile(
                                Profile(
                                    birth = viewState.value.birthDate?.format(
                                        DateTimeFormatter.ofPattern(
                                            "dd.MM.yyyy"
                                        )
                                    ) ?: "13.12.1489",
                                    name = viewState.value.name,
                                    surname = viewState.value.surname,
                                    interests = viewState.value.interests
                                )
                            )
                            setEffect { OnboardingContract.Effect.Navigation.ToMain }
                        }
                    }
                }
            }
        }
    }


    private fun setProfile(profile: Profile) {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                val profile_ = profile.copy(birth = profile.birth.replace(".", "-"))
                repository.setProfile(profile = profile_.toRequest())
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
//                        setState { copy(profile = response.data.toProfile(), isLoading = false) }
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                        viewModelScope.launch {
                            userStorage.setCategories(profile.interests.toStringSet())
                        }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState {
                        copy(
                            error = null,
                        )
                    }
                }
            }
        )
    }
}