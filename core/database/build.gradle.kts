plugins {
    alias(libs.plugins.my.android.library)
    alias(libs.plugins.my.android.hilt)
    alias(libs.plugins.my.android.room)
}

android {
    namespace = "com.jabozaroid.abopay.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "/META-INF/LICENSE.md",
                    "/META-INF/LICENSE-notice.md"
                )
            )
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.data)

    implementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    implementation(libs.gson)
}
