plugins {
    alias(libs.plugins.android.application)
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
    // Si no estás usando un archivo `libs.versions.toml`, puedes agregar las dependencias directamente
    implementation("mysql:mysql-connector-java:5.1.49") // Asegúrate de que se pueda usar para pruebas en Android, como mencioné antes

    // Otras dependencias
    implementation("androidx.appcompat:appcompat:1.6.1") // Versión más reciente disponible
    implementation("com.google.android.material:material:1.8.0") // Última versión estable
    implementation("androidx.activity:activity:1.10.0") // Última versión estable

    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Última versión

    // Dependencias de prueba
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
