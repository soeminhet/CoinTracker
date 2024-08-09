plugins {
    alias(libs.plugins.com.cointracker.application)
    alias(libs.plugins.com.cointracker.application.compose)
    alias(libs.plugins.com.cointracker.hilt)
    alias(libs.plugins.com.cointracker.uitest)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.smh.cointracker"

    defaultConfig {
        applicationId = "com.smh.cointracker"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.smh.cointracker.helper.HiltTestRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = File(rootDir, "CoinTrackerKey.keystore")
            storePassword = properties.getValue("RELEASE_KEY_STORE_PASSWORD") as String
            keyAlias = properties.getValue("RELEASE_KEY_ALIAS") as String
            keyPassword = properties.getValue("RELEASE_KEY_PASSWORD") as String
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        }

        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
    implementation(project(":core:testing"))
    implementation(project(":core:network"))
    baselineProfile(project(":benchmark"))

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.arrow)

    testImplementation(libs.junit)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.androidx.profileinstaller)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}