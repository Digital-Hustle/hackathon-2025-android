package com.sueta.main.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.model.TransportType



@Composable
fun TransportTypeChip(
    transportType: TransportType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = BorderStroke(
            width = 1.dp,
            color = if (!isSelected) MaterialTheme.colorScheme.outline else Color.Transparent
        ),
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 12.dp)
        ) {
            Icon(
                when (transportType) {
                    TransportType.WALKING -> Icons.AutoMirrored.Filled.DirectionsWalk
                    TransportType.CAR -> Icons.Default.DirectionsCar
                    TransportType.BICYCLE -> Icons.Default.PedalBike
                }, "Транспорт", tint = contentColor
            )

        }
    }
}

