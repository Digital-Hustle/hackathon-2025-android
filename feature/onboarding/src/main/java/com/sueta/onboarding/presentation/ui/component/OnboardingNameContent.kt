package com.sueta.onboarding.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.onboarding.presentation.OnboardingContract


@Composable
fun OnboardingNameContent(
    state: OnboardingContract.State,
    onEventSent: (event: OnboardingContract.Event) -> Unit,
) {
    OutlinedTextField(
        value = state.name,
        onValueChange = { onEventSent(OnboardingContract.Event.OnNameChanged(it)) },
        label = "Имя",
        hint = "Иvан"
    )
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = state.surname,
        onValueChange = { onEventSent(OnboardingContract.Event.OnSurnameChanged(it)) },
        label = "Фамилия",
        hint = "Иvаноv"
    )
    Spacer(modifier = Modifier.height(18.dp))
}