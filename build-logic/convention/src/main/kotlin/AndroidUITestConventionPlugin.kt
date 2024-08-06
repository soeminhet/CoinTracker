import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidUITestConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
                "androidTestImplementation"(libs.findLibrary("assertk").get())

                "androidTestImplementation"(libs.findLibrary("hilt.android.test").get())
                "kspAndroidTest"(libs.findLibrary("hilt.android.compiler").get())

                val bom = libs.findLibrary("androidx.compose.bom").get()
                "androidTestImplementation"(platform(bom).get())
                "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}