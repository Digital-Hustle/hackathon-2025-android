package com.sueta.onboarding.presentation.ui.component.date_picker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BirthDatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) } // 0 - дни, 1 - месяцы, 2 - годы
    var selectedDay by remember { mutableIntStateOf(selectedDate?.dayOfMonth ?: 1) }
    var selectedMonth by remember { mutableStateOf(selectedDate?.month ?: Month.JANUARY) }
    var selectedYear by remember { mutableIntStateOf(selectedDate?.year ?: 2000) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Заголовок
                Text(
                    text = "Дата рождения",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Переключатель дней/месяцев/лет
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DatePickerChip(
                        text = "$selectedDay",
                        isSelected = currentPage == 0,
                        onClick = { currentPage = 0 }
                    )

                    DatePickerChip(
                        text = selectedMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                        isSelected = currentPage == 1,
                        onClick = { currentPage = 1 }
                    )
                    DatePickerChip(
                        text = "$selectedYear",
                        isSelected = currentPage == 2,
                        onClick = { currentPage = 2 }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Контент в зависимости от выбранной страницы
                when (currentPage) {
                    0 -> DaysGrid(
                        selectedDay = selectedDay,
                        onDaySelected = { selectedDay = it }
                    )

                    1 -> MonthsGrid(
                        selectedMonth = selectedMonth,
                        onMonthSelected = { selectedMonth = it }
                    )

                    2 -> YearsGrid(
                        selectedYear = selectedYear,
                        onYearSelected = { selectedYear = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Кнопки
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val newDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
                            onDateSelected(newDate)
                            onDismiss()
                        }
                    ) {
                        Text("Готово")
                    }
                }
            }
        }
    }
}