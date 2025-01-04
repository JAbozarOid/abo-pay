plugins {
    alias(libs.plugins.my.android.library)
    alias(libs.plugins.my.android.hilt)
}

android {
    namespace = "com.jabozaroid.abopay.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(libs.kotlinx.coroutines.android)
}
