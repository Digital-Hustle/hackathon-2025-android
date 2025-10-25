package com.sueta.onboarding.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sueta.onboarding.R
import com.sueta.onboarding.presentation.OnboardingContract
import com.sueta.onboarding.presentation.OnboardingStage
import com.sueta.onboarding.presentation.ui.component.OnboardingBirthDateContent
import com.sueta.onboarding.presentation.ui.component.OnboardingInterestsContent
import com.sueta.onboarding.presentation.ui.component.OnboardingNameContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@SuppressLint("NewApi")
@Composable
fun OnboardingScreen(
    state: OnboardingContract.State,
    effectFlow: Flow<OnboardingContract.Effect>?,
    onEventSent: (event: OnboardingContract.Event) -> Unit,
    onNavigationRequested: (OnboardingContract.Effect.Navigation) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {

        effectFlow?.onEach { effect ->
            when (effect) {
                OnboardingContract.Effect.Navigation.ToMain -> onNavigationRequested(
                    OnboardingContract.Effect.Navigation.ToMain
                )
                is OnboardingContract.Effect.FieldsIsEmpty ->
                    Toast.makeText(
                        context, effect.message,
                        Toast.LENGTH_LONG
                    ).show()
            }
        }?.collect()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = state.label,
                fontSize = 25.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.8f),
                maxLines = 2,

                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.description,
                modifier = Modifier.fillMaxWidth(0.8f),
                color = MaterialTheme.colorScheme.onSecondary,
                minLines = 2
            )
            Spacer(modifier = Modifier.height(16.dp))
            when (state.currentStage) {
                OnboardingStage.NAME -> {
                    OnboardingNameContent(state,onEventSent)
                }

                OnboardingStage.BIRTH_DATE -> {
                    OnboardingBirthDateContent(state,onEventSent)
                }

                OnboardingStage.INTERESTS -> {
                    OnboardingInterestsContent(state,onEventSent)
                }
            }


            Spacer(modifier = Modifier.height(18.dp))


            Button(
                onClick = {
                    onEventSent(OnboardingContract.Event.OnNextButtonClicked)
                }, Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.16f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.next), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(40.dp))


        }
    }

}