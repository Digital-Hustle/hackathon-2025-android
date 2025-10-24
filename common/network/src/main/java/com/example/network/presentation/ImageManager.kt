package com.example.network.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Buffer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Base64

class ImageManager(private val context: Context) {

    fun prepareImageForUpload(uri: Uri): MultipartBody.Part {
        val file = getFileFromUri(context, uri)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val destinationFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return destinationFile
    }

    fun drawableToBase64(@DrawableRes drawableResId: Int): String? {
        val drawable = ContextCompat.getDrawable(context, drawableResId) ?: return null
        val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return null

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        return Base64.getEncoder().encodeToString(byteArray)
    }

    companion object {

        fun base64toBitmap(base64Image: String): Bitmap {
            val imageBytes = Base64.getDecoder().decode(base64Image)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        fun getBase64FromMultipartBodyPart(part: MultipartBody.Part): String {
            val requestBody = part.body
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val bytes = buffer.readByteArray()
            return Base64.getEncoder().encodeToString(bytes)
        }
    }
}