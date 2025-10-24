package com.example.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledRadioButton(
    selected: Boolean,
    onSelect: (Boolean) -> Unit,
    selectedText: String = "ON",
    unselectedText: String = "OFF",
    modifier: Modifier = Modifier,
    enabledColor: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { onSelect(!selected) }
            .padding(horizontal = 4.dp)
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая часть (OFF)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (!selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = unselectedText,
                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Правая часть (ON)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedText,
                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}