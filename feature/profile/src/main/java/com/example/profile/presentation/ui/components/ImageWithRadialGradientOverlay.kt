package com.example.profile.presentation.ui.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.network.ApiResponse
import com.example.profile.R
import com.example.profile.presentation.ProfileContract
//import com.example.profile.presentation.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ImageWithRadialGradientOverlay(
    state: ProfileContract.State,
    onEventSent: (event: ProfileContract.Event) -> Unit,

    ) {

    if (state.imagePickerDialogIsOpen) {
        ImagePickerDialog(onDismissRequest = { onEventSent(ProfileContract.Event.DismissImagePickerDialog) },
            onPhotoSelected = { uri ->
                onEventSent(ProfileContract.Event.ImageSelected(uri))
            })
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        contentAlignment = Alignment.Center

    ) { // Задаем фиксированную высоту для примера

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {


            if (state.profile.image != null) {
                Image(
                    bitmap = state.profile.image!!.asImageBitmap(), // Ваше изображение
                    contentDescription = "Изображение с затемнением",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Или другой подходящий ContentScale
                )
            } else if (state.imageIsLoading) {

                SimpleLoadingCircle()
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Изображение с затемнением",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
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
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (state.isEdit) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { onEventSent(ProfileContract.Event.ImageChangeButtonClicked) },
//                    Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddAPhoto,
                            contentDescription = "Localized description",
                            modifier = Modifier.fillMaxSize(0.8f)
                        )
                    }
                }
            }
            Text(state.profile.name, fontSize = 22.sp, modifier = Modifier.padding(10.dp))
        }
    }
}