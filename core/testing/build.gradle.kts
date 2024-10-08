plugins {
    alias(libs.plugins.com.cointracker.library)
    alias(libs.plugins.com.cointracker.hilt)
}

android {
    namespace = "com.smh.testing"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(libs.arrow)
    implementation(libs.kotlinx.coroutines.test)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.test.junit4)

    implementation(libs.hilt.android.test)
    implementation(libs.androidx.room.runtime)
}