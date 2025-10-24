plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
//    id("com.google.devtools.ksp")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
//    id("androidx.room")
}

android {
    namespace = "com.example.chats"
    compileSdk = 35

    defaultConfig {
        minSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        compose = true
    }
//    room {
//        schemaDirectory("$projectDir/schemas")
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.compose)

    implementation (libs.androidx.material.icons.core)
    implementation (libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3.android)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


    implementation (libs.androidx.lifecycle.viewmodel.compose.v262)

    implementation (libs.androidx.runtime.livedata.v154)

    implementation (libs.androidx.hilt.navigation.compose.v110)

    implementation (libs.coil.compose)

    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)

//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler) // Для аннотаций @Database, @Dao, @Entity
//    implementation(libs.androidx.room.ktx) // Корутин-функции для Room
//    implementation(libs.androidx.room.paging)

    implementation(project(":common:chats_holder"))
    implementation(project(":common:core"))
    implementation(project(":common:network"))




}