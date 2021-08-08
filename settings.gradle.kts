import java.net.URI

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "RickNMortyCompose"
include(":app")
 