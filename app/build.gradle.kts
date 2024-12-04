plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sportstrackerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sportstrackerapp"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.1") // Use latest version
    implementation("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    implementation(libs.room.common.jvm)
    implementation(libs.room.runtime)
    implementation(libs.play.services.games)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation (libs.retrofit)
    implementation (libs.retrofitgsonconverter)
    implementation (libs.glide)
    annotationProcessor (libs.glidecompiler)
    implementation(libs.androidsvg)
    annotationProcessor(libs.room.compiler)

}
