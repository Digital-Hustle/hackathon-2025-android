package com.sueta.main.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.model.PointType


@Composable
fun PointSelectionCards(
    state: MainContract.PointSelectionState,
    onPointClick: (PointType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Точки маршрута",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        PointCard(
            pointType = PointType.START,
            point = state.startPoint,
            isSelected = state.selectedPoint == PointType.START,
            onClick = { onPointClick(PointType.START) },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        PointCard(
            pointType = PointType.END,
            point = state.endPoint,
            isSelected = state.selectedPoint == PointType.END,
            onClick = { onPointClick(PointType.END) },
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

