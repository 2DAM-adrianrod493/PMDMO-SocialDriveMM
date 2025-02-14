plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "iesmm.pmdmo.socialdrivemm"
    compileSdk = 35

    defaultConfig {
        applicationId = "iesmm.pmdmo.socialdrivemm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {


    // Otras dependencias
    implementation(libs.appcompat.v161) // Versión más reciente disponible
    implementation(libs.material.v180) // Última versión estable
    implementation(libs.activity) // Última versión estable

    implementation(libs.constraintlayout.v214)
    implementation(libs.play.services.maps) // Última versión

    // Dependencias de prueba
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v115)
    androidTestImplementation(libs.espresso.core.v351)


    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation(libs.mysql.connector.java)
}
