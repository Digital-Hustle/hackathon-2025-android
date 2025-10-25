package com.sueta.profile.presentation.ui.components.date_picker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthsGrid(
    selectedMonth: Month,
    onMonthSelected: (Month) -> Unit
) {
    val months = Month.entries.toTypedArray()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.height(200.dp)
    ) {
        items(months) { month ->
            val monthName = month.getDisplayName(TextStyle.SHORT, Locale("ru"))
            val isSelected = month == selectedMonth

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                onClick = { onMonthSelected(month) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = monthName,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}