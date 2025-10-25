package com.sueta.profile.presentation.ui.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sueta.core.mLog
import com.sueta.profile.presentation.ProfileContract
import com.sueta.profile.presentation.ui.components.date_picker.BirthDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SuppressLint("NewApi")
@Composable
fun BirthDateInput(
    state: ProfileContract.State,
    onEventSent: (event: ProfileContract.Event) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(18.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePicker = true },
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.profile.birth,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Выбрать дату",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    Text(
        "Дата рождения", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
        modifier = Modifier.padding(vertical = 3.dp).fillMaxWidth(), textAlign = TextAlign.Start
    )
    Devider()


    if (showDatePicker) {
        mLog("TITITITITIT", "TTT")
        BirthDatePicker(
            selectedDate = state.profile.birth.toLocalDate(),
            onDateSelected = { date ->
                onEventSent(
                    ProfileContract.Event.OnBirthDateChanged(
                        date.format(
                            DateTimeFormatter.ofPattern(
                                "dd.MM.yyyy"
                            )
                        )
                    )
                )
            },
            onDismiss = { showDatePicker = false }
        )
    }

}


@SuppressLint("NewApi")
fun String.toLocalDate(): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null // возвращаем null если парсинг не удался
    }
}









