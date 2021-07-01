object Plugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val hilt = "dagger.hilt.android.plugin"

    object Spotless {
        const val plugin = "com.diffplug.spotless"
        const val version = "5.9.0"
    }

    object Detekt {
        const val plugin = "io.gitlab.arturbosch.detekt"
        const val version = "1.17.1"
    }

    object BenManes {
        const val plugin = "com.github.ben-manes.versions"
        const val version = "0.39.0"
    }
}
