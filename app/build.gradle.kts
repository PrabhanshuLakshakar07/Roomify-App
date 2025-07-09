plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.20"

}

android {
    namespace = "com.prabhanshu.roomify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.prabhanshu.roomify"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.compose.material3:material3:1.3.2")
    implementation ("androidx.compose.material:material:1.8.3")

    implementation("androidx.compose.foundation:foundation")

    // Retrofit & OkHttp
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //navition
    implementation ("androidx.navigation:navigation-compose:2.8.9")

    //shared preference dependency
    implementation ("androidx.datastore:datastore-preferences:1.1.4")
    implementation("androidx.datastore:datastore:1.1.4")
// Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //coil
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
    //serilation
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")


    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.1")

    implementation("androidx.compose.runtime:runtime:1.8.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.8.3")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.8.3")
    implementation("com.airbnb.android:lottie-compose:6.1.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")




}