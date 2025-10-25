package com.sueta.main.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.PointSelectionState
import com.sueta.main.presentation.PointType
import ru.dgis.sdk.directory.DirectoryObject


@Composable
fun PointSelectionCards(
    state: PointSelectionState,
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

