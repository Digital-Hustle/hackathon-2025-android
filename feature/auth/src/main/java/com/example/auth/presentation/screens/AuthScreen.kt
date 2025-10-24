package com.example.auth.presentation.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.R
import com.example.network.presentation.TokenViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.presentation.AuthContract
import com.example.auth.presentation.components.GoogleSignInButton
import com.example.auth.presentation.components.LabeledRadioButton
import com.example.auth.presentation.components.OutlinedPasswordTextField
import com.example.auth.presentation.components.TextWithDividers
import com.example.auth.presentation.components.LoginOutlinedTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect

@Composable
fun AuthScreen(
    state: AuthContract.State,
    effectFlow: Flow<AuthContract.Effect>?,
    onEventSent: (event: AuthContract.Event) -> Unit,
    onNavigationRequested: (AuthContract.Effect.Navigation) -> Unit,

    ) {
    val tokenViewModel: TokenViewModel = hiltViewModel()


    val labelTextLogin = stringResource(R.string.login_header)
    val descriptionTextLogin = stringResource(R.string.login_description)
    val labelTextSingUp = stringResource(R.string.sing_up_header)
    val descriptionTextSingUp = stringResource(R.string.sing_up_description)

    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {


                is AuthContract.Effect.LoginWasLoaded -> {
                    Log.d("mLogEFF",effect.token)
                    tokenViewModel.saveTokens(effect.token, effect.refreshToken)
                }

                AuthContract.Effect.RegistrationWasLoaded -> onEventSent(
                    AuthContract.Event.LoginButtonClicked
                )


                AuthContract.Effect.Navigation.toMain -> {
                    onNavigationRequested(AuthContract.Effect.Navigation.toMain)
                }


            }
        }?.collect()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {


        var label by remember { mutableStateOf(labelTextLogin) }
        var description by remember { mutableStateOf(descriptionTextLogin) }

        if (state.isRegistration) {
            label = labelTextSingUp
            description = descriptionTextSingUp

        } else {
            label = labelTextLogin
            description = descriptionTextLogin
        }


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
                text = label,
                fontSize = 25.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.8f),
                maxLines = 1,

                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(0.8f),
                color = MaterialTheme.colorScheme.onSecondary,
                minLines = 2
            )
            Spacer(modifier = Modifier.height(16.dp))


            LabeledRadioButton(
                state.isRegistration,
                { select ->
                    onEventSent(AuthContract.Event.ChangeAuthTypeButtonClicked(select))
                },
                selectedText = stringResource(R.string.SingUp),
                unselectedText = stringResource(R.string.login)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginOutlinedTextField(
                state.login,
                { onEventSent(AuthContract.Event.LoginChanged(it)) },
                stringResource(R.string.email),
                stringResource(R.string.email_example)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedPasswordTextField(
                value = state.password,
                onValueChange = { onEventSent(AuthContract.Event.PasswordChanged(it)) },
                label = stringResource(R.string.password)
            )
            Spacer(modifier = Modifier.height(18.dp))


            AnimatedVisibility(
                visible = state.isRegistration,
            ) {

                OutlinedPasswordTextField(
                    value = state.passwordConfirmation,
                    onValueChange = { onEventSent(AuthContract.Event.PasswordConfirmationChanged(it)) },
                    stringResource(R.string.confirm_password),
                    changeVisibleButton = false
                )
            }

            AnimatedVisibility(visible = state.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.error?:"",
                    modifier = Modifier.fillMaxWidth(0.8f),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,

                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(18.dp))


            Button(
                onClick = {
                    if (state.isRegistration) {
                        onEventSent(
                            AuthContract.Event.RegistrationButtonClicked
                        )
                    } else {
                        onEventSent(
                            AuthContract.Event.LoginButtonClicked
                        )


                    }
                }, Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.16f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.next), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextWithDividers(stringResource(R.string.or_next_with))
            Spacer(modifier = Modifier.height(16.dp))
            GoogleSignInButton({
                onEventSent(AuthContract.Event.LoginWithGoogleButtonClicked)
            })
            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}








