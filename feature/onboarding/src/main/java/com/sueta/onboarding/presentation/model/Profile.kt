package com.sueta.onboarding.presentation.model

import com.sueta.onboarding.presentation.Interest

data class Profile(
    var birth: String = "",
    var name: String = "",
    val surname :String = "",
    val interests: Set<Interest> = setOf()
)
