plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)


    id("kotlin-kapt")
//    id("com.google.devtools.ksp")

    id("com.google.dagger.hilt.android")
}
//val BACKEND_URL: String by project
val WEB_CLIENT_ID :String by project

val BACKEND_HOST_PORT: String by project

val backendUrl = "http://$BACKEND_HOST_PORT"
val socketBackendURL = "ws://$BACKEND_HOST_PORT/ws"
//val USE_MOCKS :Boolean by project
android {
    namespace = "com.sueta.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

//        buildConfigField(
//            "Boolean",
//            "USE_MOCKS",
//            "false"
//        )
        buildConfigField("String", "BACKEND_URL", "\"$backendUrl\"")
        buildConfigField("String", "SOCKET_BACKEND_URL", "\"$socketBackendURL\"")

        buildConfigField("String", "WEB_CLIENT_ID", "\"$WEB_CLIENT_ID\"")
        val USE_MOCKS = (project.findProperty("USE_MOCKS") as? String)?.toBoolean() ?: false
        buildConfigField("Boolean", "USE_MOCKS", "$USE_MOCKS")
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)





}