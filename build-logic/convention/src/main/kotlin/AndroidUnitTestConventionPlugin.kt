import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
class AndroidUnitTestConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("de.mannodermaus.android-junit5")
            }

            extensions.configure<LibraryExtension> {
                testOptions {
                    unitTests {
                        isReturnDefaultValues = true
                        isIncludeAndroidResources = true
                    }
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "testImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "testRuntimeOnly"(libs.findLibrary("junit.jupiter.engine").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.params").get())

                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("mockwebserver").get())

                "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                "testImplementation"(libs.findLibrary("turbine").get())
                "testImplementation"(libs.findLibrary("assertk").get())
            }
        }
    }
}