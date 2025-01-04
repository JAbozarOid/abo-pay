plugins {
    alias(libs.plugins.my.android.library)
    alias(libs.plugins.my.android.library.compose)
    alias(libs.plugins.my.android.hilt)
}

android {
    namespace = "com.jabozaroid.abopay.core.analytics"
}

dependencies {
    implementation(projects.core.common)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.mobmetricalib)
    implementation(libs.gson)
    implementation(libs.metrix)

    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

}
