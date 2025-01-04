import com.android.build.gradle.LibraryExtension
import com.jabozaroid.abopay.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("my.android.library")
                apply("my.android.hilt")
            }
            extensions.configure<LibraryExtension> {

            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("testImplementation", project(":core:ui"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:common"))

                add("implementation", libs.findLibrary("coil.kt").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("constraintlayout-compose").get())

                add("testImplementation", libs.findLibrary("mockk-agent").get())
                add("testImplementation", libs.findLibrary("mockk-android").get())
                add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
                add("testImplementation", project(":core:test"))
                add("androidTestImplementation", project(":core:test"))
            }
        }
    }
}
