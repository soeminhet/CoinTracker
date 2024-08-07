plugins {
    alias(libs.plugins.com.cointracker.feature)
    alias(libs.plugins.com.cointracker.library.compose)
    alias(libs.plugins.com.cointracker.unittest)
}

android {
    namespace = "com.smh.detail"
}

dependencies {
    implementation(project(":core:network"))
}