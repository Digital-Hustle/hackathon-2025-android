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
import com.sueta.main.data.toStringAddress
import com.sueta.main.presentation.PointType
import ru.dgis.sdk.directory.DirectoryObject

@Composable
fun PointCard(
    pointType: PointType,
    point: DirectoryObject?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {


    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Иконка и label
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = when (pointType) {
                        PointType.START -> Icons.Default.Place
                        PointType.END -> Icons.Default.Flag
                        else -> Icons.Default.LocationOn
                    }, contentDescription = when (pointType) {
                        PointType.START -> "Начальная точка"
                        PointType.END -> "Конечная точка"
                        else -> "Точка"
                    }, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = when (pointType) {
                            PointType.START -> "Откуда"
                            PointType.END -> "Куда"
                            else -> "Точка"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val default = if (pointType == PointType.START) "Начальная точка" else "Конечная точка"
                    val address = if (point!=null) (point?.title ) +" • " + point?.toStringAddress() else
                        default
                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                }
            }
        }
    }
}