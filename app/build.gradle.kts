plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-kapt")}

android {
    namespace = "net.iessochoa.davidpagan.practica5"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.iessochoa.davidpagan.practica5"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    // Room components
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.preference:preference:1.2.1")
    kapt ("androidx.room:room-compiler:2.6.0")

    implementation ("androidx.preference:preference-ktx:1.2.1")

// Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    //corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core: 1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android: 1.7.3")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
// Activity
    implementation ("androidx.activity:activity-ktx:1.8.0")
// LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("com.android.databinding:viewbinding:8.1.2")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}