package com.sueta.profile.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sueta.network.presentation.TokenViewModel
import com.sueta.profile.presentation.ProfileContract
import com.sueta.profile.presentation.model.Sex
//import com.example.profile.presentation.ProfileViewModel
import com.sueta.profile.presentation.ui.components.ImageWithRadialGradientOverlay
import com.sueta.profile.presentation.ui.components.LabeledRadioButton
import com.sueta.profile.presentation.ui.components.ProfileItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileContract.State,
    effectFlow: Flow<ProfileContract.Effect>?,
    onEventSent: (event: ProfileContract.Event) -> Unit,
    onNavigationRequested: (ProfileContract.Effect.Navigation) -> Unit,
) {

    val tokenViewModel :TokenViewModel = hiltViewModel()



    LaunchedEffect(Unit) {
        onEventSent(ProfileContract.Event.LoadProfile(state.username))

        effectFlow?.onEach { effect ->
            when (effect) {
                ProfileContract.Effect.Navigation.ToMain -> onNavigationRequested(
                    ProfileContract.Effect.Navigation.ToMain
                )

                ProfileContract.Effect.Logout -> tokenViewModel.deleteToken()
            }
        }?.collect()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = {
                onEventSent(ProfileContract.Event.BackButtonClicked)
            }, Modifier.fillMaxHeight()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }
        }, title = {
            Text(
                "Профиль", fontWeight = FontWeight.Bold

            )

        }, colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
            scrolledContainerColor = MaterialTheme.colorScheme.onTertiary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ), actions =
        {
            IconButton(onClick = { onEventSent(ProfileContract.Event.OnMenuBottonClicked) }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Меню")
            }
            DropdownMenu(
            expanded = state.showMenu,
            onDismissRequest = { onEventSent(ProfileContract.Event.OnDismissMenu) },
                Modifier.background(MaterialTheme.colorScheme.tertiary)
            ) {
            DropdownMenuItem(
                text = {  Text("Выйти")  },
                onClick = {
                    onEventSent(ProfileContract.Event.OnDismissMenu)
                    onEventSent(ProfileContract.Event.Logout)
                }
            )
            }

        })



        ImageWithRadialGradientOverlay(
            state,
            onEventSent,
        )


        Column(Modifier.fillMaxWidth(0.85f)) {
            Text(
                "Информация",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
            if (state.isEdit) {
                ProfileItem(
                    state.profile.name,
                    "Имя",
                    editable = state.isEdit,
                    onValueChanged = { newValue ->
                        onEventSent(ProfileContract.Event.NameChanged(newValue))
                    }
                )
            }





            ProfileItem(
                state.profile.description,
                "О себе",
                maxLines = 4,
                editable = state.isEdit,
                onValueChanged = { newValue ->
                    onEventSent(ProfileContract.Event.DescriptionChanged(newValue))

                }
            )
            ProfileItem(
                state.profile.age,
                "Возраст",
                editable = state.isEdit,
                onValueChanged = { newValue ->
                    onEventSent(ProfileContract.Event.AgeChanged(newValue))

                },
                isNumericInput = true
            )
            if (!state.isEdit) {
                ProfileItem(
                    if (state.profile.sex == Sex.MALE) "Mужчина" else "Женщина",
                    "Пол",
                )
            } else Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabeledRadioButton(
                    state.profile.sex != Sex.MALE,
                    { select: Boolean ->
                        onEventSent(ProfileContract.Event.SexChanged(if (select) 0 else 1))
                    },
                    "Женщина",
                    "Мужчина"
                )

            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .alpha(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .clickable {
                    if (state.isEdit) {
                        onEventSent(ProfileContract.Event.EditIsOff)
                        onEventSent(ProfileContract.Event.SaveProfile(state.profile))
                    } else onEventSent(ProfileContract.Event.EditIsOn)
                }) {
                Row(
                    modifier = Modifier.padding(
                        start = 7.dp, end = 9.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    Icon(
                        imageVector = if (!state.isEdit) Icons.Filled.Edit else Icons.Filled.Done,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(if (!state.isEdit) "Изменить" else "Сохранить")
                }
            }
        }


    }
}








