package utility

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlin() {
    configure<KotlinAndroidProjectExtension> {
        val warningsAsErrors: String? by project
        compilerOptions.apply {
            this.jvmTarget = JvmTarget.JVM_17
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs.add(
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            )
        }
    }
}

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        configure<ComposeCompilerGradlePluginExtension> {
            enableStrongSkippingMode = true
            includeSourceInformation = true
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom).get())
            "implementation"(libs.findLibrary("androidx.ui").get())
            "implementation"(libs.findLibrary("androidx.ui.graphics").get())
            "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
            "implementation"(libs.findLibrary("androidx.material3").get())
            "implementation"(libs.findLibrary("androidx.material").get())
            "implementation"(libs.findLibrary("material").get())
            "debugImplementation"(libs.findLibrary("ui.tooling").get())
        }
    }
}
