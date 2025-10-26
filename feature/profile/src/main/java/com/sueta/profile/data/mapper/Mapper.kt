package com.sueta.profile.data.mapper

import com.sueta.network.presentation.ImageManager
import com.sueta.profile.domain.model.ProfileRequest
import com.sueta.profile.domain.model.ProfileResponse
import com.sueta.profile.presentation.model.Interest
import com.sueta.profile.presentation.model.Profile

fun ProfileResponse.toProfile(): Profile {
    return Profile(
        name = name ?: "",
        birth = birthDate ?: "",
        surname = surname ?: "",
        interests = interest?.toInterestSet() ?: emptySet(),
        image = image?.let { ImageManager.base64toBitmap(it) }

    )
}

fun Profile.toRequest(): ProfileRequest {
    return ProfileRequest(
        name = name,
        birthDate = birth,
        surname = surname,
        interest = interests.toStringList(),
    )

}

fun Set<Interest>.toStringList(): List<String> {
    return this.map { it.value }
}

fun List<String>.toInterestSet(): Set<Interest> {
    return this.mapNotNull { string ->
        Interest.entries.find { it.value == string }
    }.toSet()
}

fun Set<Interest>.toStringSet(): Set<String> {
    return this.map { it.value }.toSet()
}

fun Set<String>.toInterestSet(): Set<Interest> {
    return this.mapNotNull { string ->
        Interest.entries.find { it.value == string }
    }.toSet()
}