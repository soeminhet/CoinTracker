plugins {
    alias(libs.plugins.com.cointracker.library)
    alias(libs.plugins.com.cointracker.hilt)
    id("kotlinx-serialization")
}

val BASE_URL: String by project
android {
    namespace = "com.smh.network"
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    api(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.json)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    implementation(libs.arrow)
}