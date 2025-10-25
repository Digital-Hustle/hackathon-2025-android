package com.sueta.onboarding.presentation

import androidx.compose.ui.text.intl.Locale
import com.sueta.core.presentation.ViewEvent
import com.sueta.core.presentation.ViewSideEffect
import com.sueta.core.presentation.ViewState
import java.time.LocalDate

class OnboardingContract {

    sealed class Event : ViewEvent {

        data class OnNameChanged(val query: String) : Event()
        data class OnSurnameChanged(val query: String) : Event()
        data class OnBirthDateChanged(val query: LocalDate) : Event()
        data class OnInterestChipClick(val interest: Interest) : Event()
        data object OnNextButtonClicked : Event()
    }

    data class State(
        val isNewUser: Boolean? = null,
        val currentStage: OnboardingStage = OnboardingStage.NAME,
        val label: String = "Добро Пожаловать! Расскажите о себе",
        val description: String = "Это поможет нам создать идеальный маршрут для вас",
        val name: String = "",
        val surname: String = "",
        val birthDate: LocalDate? = null,
        val interests: Set<Interest> = setOf(),
        val error: String? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
        }

        data class FieldsIsEmpty(val message:String): Effect()


    }



}