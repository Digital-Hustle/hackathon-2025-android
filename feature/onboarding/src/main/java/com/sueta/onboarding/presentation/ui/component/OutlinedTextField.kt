package com.sueta.onboarding.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(

        value = value,
        onValueChange = onValueChange,
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
