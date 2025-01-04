plugins {
    alias(libs.plugins.my.android.application)
    alias(libs.plugins.my.android.application.compose)
}

android {
    defaultConfig {
        applicationId = "com.jabozaroid.abopay.uiKit"
        versionCode = 1
        versionName = "0.0.1"
        minSdk = 24

        missingDimensionStrategy(
            com.jabozaroid.abopay.FlavorDimension.Market.name,
            com.jabozaroid.abopay.AboPayFlavor.Demo.name
        )

    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.sadadpsp.task.uiKit"
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(libs.androidx.activity.compose)
}