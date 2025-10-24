package com.example.profile.data.mapper

import com.example.network.presentation.ImageManager
import com.example.profile.domain.model.ProfileRequest
import com.example.profile.domain.model.ProfileResponse
import com.example.profile.presentation.model.Profile
import com.example.profile.presentation.model.Sex

fun ProfileResponse.toProfile(): Profile {
    return Profile(
        age = age?.toString() ?: "",
        description = description ?: "",
        name = name ?: "",
        sex = Sex.fromId( sex?:1 )?: Sex.MALE,
        image = image?.let { ImageManager.base64toBitmap(it) }
    )
}

fun Profile.toRequest(): ProfileRequest {
    return ProfileRequest(
        age = age.ifEmpty { "0" }.toInt(),
        description = description,
        name = name,
        sex = sex.id
    )

}