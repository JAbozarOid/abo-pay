plugins {
    alias(libs.plugins.my.android.feature)
    alias(libs.plugins.my.android.library.compose)
}

android {
    namespace = "com.jabozaroid.abopay.feature.login"
}

dependencies {
    implementation(libs.gson)

}