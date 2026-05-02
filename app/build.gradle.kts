plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.mycalculatorapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mycalculatorapp"
        minSdk = 21
        targetSdk = 36 // Updated to match compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.mycalculatorapp.CucumberTestRunner"
        testInstrumentationRunnerArguments["features"] = "features"
        testInstrumentationRunnerArguments["package"] = "com.example.mycalculatorapp"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
}

dependencies {
    // Core library desugaring for Java 8+ API support on older Android versions
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.gridlayout:gridlayout:1.0.0")

    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp for networking
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // Markwon for Markdown rendering
    implementation("io.noties.markwon:core:4.6.2")

    // exp4j for mathematical expression evaluation
    implementation("net.objecthunter:exp4j:0.4.8")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.cucumber.android)
    androidTestImplementation(libs.cucumber.java)
    androidTestImplementation(libs.cucumber.picocontainer)
    androidTestImplementation(libs.androidx.test.runner)
}
