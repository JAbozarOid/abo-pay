pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:common")
include(":core:designsystem")
include(":core:database")
include(":core:model")
include(":core:ui")
include(":uiKit")
include(":core:analytics")
include(":core:domain")
include(":core:data")
include(":core:offlinestorage")
include(":core:network")
include(":core:test")
include(":feature:intro")
include(":feature:home")
include(":feature:cardtocard")

include(":feature:security")
//include(":feature:first")
include(":feature:webview")
include(":feature:login")
include (":dexguard")
include(":core:notification")
include(":feature:charge")
include(":feature:bill")
include(":feature:payment")
include(":feature:internet")
include(":feature:balance")
include(":feature:cardmanagement")
include(":feature:kahroba")
