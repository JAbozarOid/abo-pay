plugins {
    alias(libs.plugins.my.android.feature)
    alias(libs.plugins.my.android.library.compose)
}

android {
    namespace = "com.jabozaroid.abopay.feature.cardmanagement"
}

dependencies {
    implementation(libs.gson)

}