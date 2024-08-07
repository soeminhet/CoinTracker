plugins {
    alias(libs.plugins.com.cointracker.feature)
    alias(libs.plugins.com.cointracker.library.compose)
    alias(libs.plugins.com.cointracker.room)
    alias(libs.plugins.com.cointracker.unittest)
    alias(libs.plugins.com.cointracker.uitest)
}

android {
    namespace = "com.smh.home"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:testing"))
}