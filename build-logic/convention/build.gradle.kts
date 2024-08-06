import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.smh.buildlogic"
version = "1.0"

repositories {
    mavenCentral()
    google()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "com.cointracker.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "com.cointracker.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "com.cointracker.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "com.cointracker.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeature") {
            id = "com.cointracker.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidRoom") {
            id = "com.cointracker.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidApplication") {
            id = "com.cointracker.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidUnitTest") {
            id = "com.cointracker.unittest"
            implementationClass = "AndroidUnitTestConventionPlugin"
        }

        register("androidUITest"){
            id = "com.cointracker.uitest"
            implementationClass = "AndroidUITestConventionPlugin"
        }
    }
}