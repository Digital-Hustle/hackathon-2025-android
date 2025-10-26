package com.sueta.main.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sueta.main.R
import com.sueta.main.presentation.MainContract
import com.sueta.network.presentation.ImageManager


@Composable
fun ImageWithRadialGradientOverlay(
    image:String?,
    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        contentAlignment = Alignment.Center

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
                if (image != null){
                Image(
                    bitmap = ImageManager.base64toBitmap(image).asImageBitmap(), // Ваше изображение
                    contentDescription = "Изображение с затемнением",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Или другой подходящий ContentScale
                )}

                else {
                    Image(
                    painter = painterResource(R.drawable.profile_picture),
                    contentDescription = "Изображение с затемнением",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                }
            }


            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    brush = Brush.verticalGradient( // Вертикальный градиент
                        colors = listOf(
                            Color.Transparent, // Сверху прозрачный
                            Color.Black.copy(alpha = 0.6f) // Снизу черный с прозрачностью
                        ), startY = 0f, endY = size.height  // От верха до низа
                    )
                )

            }
        }

    }
