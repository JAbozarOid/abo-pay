plugins {
    alias(libs.plugins.my.android.library)
    alias(libs.plugins.my.android.hilt)
}

android {
    namespace = "com.sweet.iva.core.stream"
}

dependencies {
    implementation(projects.core.domain)
}
