

plugins {
    alias(libs.plugins.my.android.library)
    alias(libs.plugins.my.android.library.jacoco)
    alias(libs.plugins.my.android.hilt)
}

android {
    namespace = "com.jabozaroid.abopay.core.domain"
}

dependencies {
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
}