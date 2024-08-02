plugins {
    alias(libs.plugins.com.cointracker.feature)
    alias(libs.plugins.com.cointracker.library.compose)
    alias(libs.plugins.com.cointracker.room)
    alias(libs.plugins.com.cointracker.unittest)
}

android {
    namespace = "com.smh.home"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    androidTestImplementation(libs.assertk)
}