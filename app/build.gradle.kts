plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    id("com.google.gms.google-services")
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "m.a.nobahar"
    compileSdk = 35

    defaultConfig {
        applicationId = "m.a.nobahar"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "Market", "\"Myket\"")
        }
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "Market", "\"Myket\"")
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
        buildConfig = true
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
    implementation(libs.compilot.runtime)
    implementation(libs.compilot.navigation)
    ksp(libs.compilot.compiler)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material.navigation)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.compose.shimmer)
    implementation(libs.material3.window.size)
    implementation(libs.material.icons.extended)
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material)
    implementation(libs.glance.appwidget.preview)
    implementation(libs.glance.preview)
    implementation(libs.capturable)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    implementation(libs.analytics)

    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.exoplayer)

    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.retrofit.converter)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}