package com.sueta.onboarding.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.core.mLog
import com.sueta.onboarding.presentation.OnboardingContract
import com.sueta.onboarding.presentation.ui.component.date_picker.BirthDatePicker
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OnboardingBirthDateContent(
    state: OnboardingContract.State,
    onEventSent: (event: OnboardingContract.Event) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(18.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable { showDatePicker = true },
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.birthDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "дд.мм.гггг",
                color = if (state.birthDate != null) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Выбрать дату",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    if (showDatePicker) {
        mLog("TITITITITIT","TTT")
        BirthDatePicker(
            selectedDate = state.birthDate,
            onDateSelected = { date ->
                onEventSent(OnboardingContract.Event.OnBirthDateChanged(date))
            },
            onDismiss = { showDatePicker = false }
        )
    }

}










