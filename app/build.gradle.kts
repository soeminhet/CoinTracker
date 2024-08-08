plugins {
    alias(libs.plugins.com.cointracker.application)
    alias(libs.plugins.com.cointracker.application.compose)
    alias(libs.plugins.com.cointracker.hilt)
    alias(libs.plugins.com.cointracker.uitest)
}

android {
    namespace = "com.smh.cointracker"

    defaultConfig {
        applicationId = "com.smh.cointracker"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.smh.cointracker.helper.HiltTestRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
    implementation(project(":core:testing"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.arrow)

    testImplementation(libs.junit)
    debugImplementation(libs.androidx.ui.tooling)
}