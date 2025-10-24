
package com.example.auth.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement




@Composable
fun TextWithDividers(
    text: String,
    modifier: Modifier = Modifier,
    dividerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    dividerThickness: Dp = 1.dp,
    dividerWidth: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Левый штрих
        HorizontalDivider(
            modifier = Modifier.width(dividerWidth),
            thickness = dividerThickness,
            color = dividerColor
        )

        // Отступ между штрихом и текстом
        Spacer(modifier = Modifier.width(16.dp))

        // Текст посередине
        Text(
            text = text,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )

        // Отступ между текстом и штрихом
        Spacer(modifier = Modifier.width(16.dp))

        // Правый штрих
        HorizontalDivider(
            modifier = Modifier.width(dividerWidth),
            thickness = dividerThickness,
            color = dividerColor
        )
    }
}