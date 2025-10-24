package com.example.chat.presentation.ui.component

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.chat.R
import com.example.network.presentation.ImageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun Avatar(image: String?, modifier: Modifier = Modifier) {
    if (image != null) {
        var isVisible by remember { mutableStateOf(false) }
        var imageBitmap: Bitmap? by remember { mutableStateOf(null) }
        LaunchedEffect(image) {
            withContext(Dispatchers.IO) {
                imageBitmap = ImageManager.base64toBitmap(image)
            }


            delay(300)
            isVisible = true
        }
        if (!isVisible) {
            Box(
                modifier = modifier,
            )
        }
        if(imageBitmap != null){
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(), modifier = modifier
        ) {

            Image(
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = "Картинка из Base64",
                modifier = modifier.clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }}
    } else {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "Аватар",
            modifier = modifier
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )
    }
}