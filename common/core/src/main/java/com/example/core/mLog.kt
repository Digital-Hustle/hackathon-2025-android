package com.example.core

import android.util.Log

fun mLog(title: String, msg: String = "") {
    Log.d("mLog$title", msg.ifEmpty { title })
}