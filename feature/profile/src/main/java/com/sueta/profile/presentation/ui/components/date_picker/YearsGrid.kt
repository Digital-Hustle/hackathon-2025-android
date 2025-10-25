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
import java.time.Year

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YearsGrid(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    val currentYear = Year.now().value
    val startYear = currentYear - 100
    val endYear = currentYear - 10

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.height(200.dp)
    ) {
        items((startYear..endYear).toList().reversed()) { year ->
            val isSelected = year == selectedYear

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                onClick = { onYearSelected(year) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "$year",
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}