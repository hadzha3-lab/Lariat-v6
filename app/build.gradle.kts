plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "app.lariat"
    compileSdk = 36
    defaultConfig {
        applicationId = "app.lariat"
        minSdk = 23
        targetSdk = 36
        versionCode = 6
        versionName = "0.6.0"
    }
    buildFeatures { compose = true }
}
dependencies {
    val bom = platform("androidx.compose:compose-bom:2025.08.00")
    implementation(bom)
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
