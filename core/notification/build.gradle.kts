plugins {
    alias(libs.plugins.my.android.feature)
    alias(libs.plugins.my.android.library.compose)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.jabozaroid.abopay.notification"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(projects.core.offlinestorage)
    implementation(libs.androidx.test.ext)
    implementation(libs.firebase.cloud.messaging)
    api(platform(libs.firebase.bom))
    implementation(libs.appcompat)
}