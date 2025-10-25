package com.sueta.profile.presentation.ui.components.date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DaysGrid(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val daysInMonth = 31 // Упрощенно, можно сделать логику для разных месяцев

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(200.dp)
    ) {
        items(35) { index ->
            val day = index + 1
            val isSelectable = day <= daysInMonth

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clickable(isSelectable) { if (isSelectable) onDaySelected(day) },
                contentAlignment = Alignment.Center
            ) {
                if (isSelectable) {
                    val isSelected = day == selectedDay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primary
                                else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}