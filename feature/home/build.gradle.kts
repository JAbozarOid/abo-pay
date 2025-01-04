plugins {
    alias(libs.plugins.my.android.feature)
    alias(libs.plugins.my.android.library.compose)
}

android {
    namespace = "com.jabozaroid.abopay.feature.home"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.feature.webview)

    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

}