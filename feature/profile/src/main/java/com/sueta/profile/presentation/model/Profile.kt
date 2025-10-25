package com.sueta.profile.presentation.model

import android.graphics.Bitmap

enum class Sex(val id: Int) {
    MALE(1),
    FEMALE(0);

    companion object {
        fun fromId(id: Int): Sex? = entries.find { it.id == id }
    }
}


data class Profile(
    var age: String = "",
    var description: String = "",
    var name: String = "",
    var sex: Sex = Sex.MALE,
    var image: Bitmap? = null,
)
