plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.runningtrackerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.runningtrackerapp"
        minSdk = 24
        targetSdk = 34
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
        viewBinding = true
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

    // Material Design
    implementation("com.google.android.material:material:1.10.0")

    implementation("androidx.lifecycle:lifecycle-service:2.6.2")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("pub.devrel:easypermissions:3.0.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")

    // Architectural Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Room KTX (mới nhất)
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines Core (mới nhất)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Coroutines Android (mới nhất)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Google Maps & Location Services
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Dagger Core
    implementation("com.google.dagger:dagger:2.44")
    kapt("com.google.dagger:dagger-compiler:2.44")
    api("com.google.dagger:dagger-android:2.44")
    api("com.google.dagger:dagger-android-support:2.44")
    kapt("com.google.dagger:dagger-android-processor:2.44")

    // Activity KTX for viewModels
    implementation("androidx.activity:activity-ktx:1.2.4")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")


    implementation("jakarta.inject:jakarta.inject-api:2.0.1")

//     EasyPermissions
    implementation("pub.devrel:easypermissions:3.0.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

//    // MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Lifecycle ViewModel và LiveData (thay thế extensions cũ)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

}