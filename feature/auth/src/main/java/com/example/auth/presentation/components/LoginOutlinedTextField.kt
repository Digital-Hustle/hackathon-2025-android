package com.example.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun LoginOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit, label: String, hint: String, modifier: Modifier = Modifier
) {
    OutlinedTextField(

        value = value,
        onValueChange =onValueChange,
        label = { Text(label) },
        placeholder = { Text(hint) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,


            ),
        modifier = modifier.fillMaxWidth(0.8f),
        singleLine = true
    )
}
