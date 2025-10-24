package com.example.profile.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ProfileItem(
    text: String,
    description: String,
    maxLines: Int = 1,
    editable: Boolean = false,
    onValueChanged: (String) -> Unit = { _ -> },
    isNumericInput: Boolean = false,

) {


    Column {
        if (editable) {
            OutlinedTextField(

                value = text,
                onValueChange = { newValue ->
                    if (isNumericInput){
                        newValue.filter { it.isDigit() }.let {
                            if (it.length <= 3) {
                                onValueChanged(it)
                            }
                        }
                    }else{
                        onValueChanged(newValue)
                    }

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,


                    ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isNumericInput) KeyboardType.Number
                    else KeyboardType.Text
                )
            )
        } else {
            Text(
                text.apply {
                    if (isNumericInput) {
                        ifEmpty { "0" }
                    }
                },
                maxLines = maxLines,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }


        Text(
            description, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
            modifier = Modifier.padding(vertical = 3.dp),
        )
        Devider()
    }
}