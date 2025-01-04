
plugins {
    alias(libs.plugins.my.android.library)
}

android {
    namespace = "com.jabozaroid.abopay.core.test"
}

dependencies {
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.test)
    api(libs.junit4)
    api(libs.mockk.android)
    api(libs.mockk.agent)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
}
