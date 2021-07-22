plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.Apollo.plugin).version(Plugins.Apollo.version)
}

android {
    compileSdk = Config.compileSdkVersion
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    kotlinOptions {
        jvmTarget = "11"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes.add("/META-INF/AL2.0")
        resources.excludes.add("/META-INF/LGPL2.1")
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {

    // Android
    implementation(Dependencies.Android.ktx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)

    // Navigation
    implementation(Dependencies.Navigation.dependency)

    // Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.paging)
    implementation(Dependencies.Compose.iconsExtended)

    // Lifecycle
    implementation(Dependencies.Lifecycle.runtime)

    // Koin
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.1.2")
    testImplementation("io.insert-koin:koin-test:3.1.2")

    // Apollo graphql
    implementation(Dependencies.Apollo.runtime)
    implementation(Dependencies.Apollo.coroutine)
    implementation(Dependencies.OkHttp.bom)
    implementation(Dependencies.OkHttp.dependency)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    // Accompanist
    implementation(Dependencies.Accompanist.coil)

    // Testing
    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.junitAndroid)
    androidTestImplementation(Dependencies.Testing.espresso)
    androidTestImplementation(Dependencies.Testing.junitCompose)
}

apollo {
    // instruct the compiler to generate Kotlin models
    generateKotlinModels.set(true)
}