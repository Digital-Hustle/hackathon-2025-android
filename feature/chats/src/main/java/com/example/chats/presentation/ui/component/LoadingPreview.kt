package com.example.chats.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingPreview(){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            "Загрузка",
//            fontSize = 25.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
        SimpleLoadingCircle()
    }
}

