package com.sueta.profile.presentation.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
//import com.example.profile.presentation.ProfileViewModel
import java.io.File

@Composable
fun ImagePickerDialog(
    onPhotoSelected: (Uri) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onPhotoSelected(uri)
            onDismissRequest()

        }
    }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri?.let {
                onPhotoSelected(photoUri!!)

                onDismissRequest()

            }
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,

        ) {
        Column(
            modifier = Modifier

//                .padding(16.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(MaterialTheme.colorScheme.tertiary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    launcher.launch("image/*")

                }
            ) { Text("Выбрать из галереи") }
            Devider()
            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    val tmpFile = File.createTempFile(
                        "JPEG_${System.currentTimeMillis()}_",
                        ".jpg",
                        context.cacheDir
                    ).apply {
                        createNewFile()
                        deleteOnExit()
                    }

                    photoUri = FileProvider.getUriForFile(
                        context,
                        "${context.applicationContext.packageName}.fileprovider",
                        tmpFile
                    )

                    // Запускаем камеру с указанным URI
                    cameraLauncher.launch(photoUri)
                }) { Text("Сделать фото") }

        }

    }

}