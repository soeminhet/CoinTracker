plugins {
    alias(libs.plugins.com.cointracker.library)
    alias(libs.plugins.com.cointracker.library.compose)
}

android {
    namespace = "com.smh.design"
}

dependencies {
    implementation(libs.coil.compose)
}