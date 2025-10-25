package com.sueta.onboarding.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.onboarding.presentation.Interest
import com.sueta.onboarding.presentation.OnboardingContract


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingInterestsContent(
    state: OnboardingContract.State,
    onEventSent: (event: OnboardingContract.Event) -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalArrangement = Arrangement.Center
    ) {
        Interest.entries.forEach {
            InterestChip(
                text = it.value,
                isSelected =it in state.interests ,
                onClick = { onEventSent(OnboardingContract.Event.OnInterestChipClick(it)) })
        }
    }
    Spacer(modifier = Modifier.height(18.dp))

}