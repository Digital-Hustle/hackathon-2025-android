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
    var birth: String = "",
    val surname :String = "",
    val interests: Set<Interest> = setOf(),
    var name: String = "",
    var image: Bitmap? = null,
)

enum class Interest(val value:String) {
    NATURE("Природа"),
    CULTURE("Культура"),
    FOOD("Гастрономия"),
    SHOPPING("Шопинг"),
    HISTORICAL("История"),
    ENTERTAINMENT("Развлечения");
}
