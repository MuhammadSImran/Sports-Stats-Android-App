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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.retrofit)
    implementation (libs.retrofitgsonconverter)
    implementation (libs.glide)
    annotationProcessor (libs.glidecompiler)
    implementation(libs.androidsvg)

}