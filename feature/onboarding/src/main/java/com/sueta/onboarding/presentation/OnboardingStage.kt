package com.sueta.onboarding.presentation

enum class OnboardingStage {
    NAME, BIRTH_DATE, INTERESTS
}

enum class Interest(val value:String) {
    NATURE("Природа"),
    CULTURE("Культура"),
    FOOD("Гастрономия"),
    SHOPPING("Шопинг"),
    HISTORICAL("История"),
    ENTERTAINMENT("Развлечения");
}