plugins {
    alias(libs.plugins.com.cointracker.feature)
    alias(libs.plugins.com.cointracker.library.compose)
}

android {
    namespace = "com.smh.detail"
}

dependencies {
    implementation(project(":core:network"))
}