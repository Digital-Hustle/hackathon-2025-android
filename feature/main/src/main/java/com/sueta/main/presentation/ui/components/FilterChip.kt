package com.sueta.main.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class BudgetType(val value: String) {
    ECONOMY("Эконом"),
    COMFORT("Комфорт"),
    PREMIUM("Премиум")
}


enum class TravelStyle(val value:String) {
    FAMILY("Семейный"),
    ACTIVE("Активный"),
    CULTURAL("Культурный"),
    ROMANTIC("Романтический"),
    GASTRONOMIC("Гастрономический"),
    PHOTOGRAPHIC("Фотографический")
}


private fun Set<TravelStyle>.toggleSelection(style: TravelStyle): Set<TravelStyle> {
    return if (this.contains(style)) {
        this - style
    } else {
        this + style
    }
}


@Composable
fun FilterChip(
    text: String,
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
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}

