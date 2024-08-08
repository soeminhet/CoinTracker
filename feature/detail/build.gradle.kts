plugins {
    alias(libs.plugins.com.cointracker.feature)
    alias(libs.plugins.com.cointracker.library.compose)
    alias(libs.plugins.com.cointracker.unittest)
    alias(libs.plugins.com.cointracker.uitest)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.smh.detail"

    sourceSets.configureEach {
        kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin/")
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:testing"))

    implementation(project(":annotation"))
    ksp(project(":processor"))
    implementation(libs.symbol.processing.api)
}