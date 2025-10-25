pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://artifactory.2gis.dev/sdk-maven-release")
        }
    }
}

rootProject.name = "SuetaRND"
include(":app")
include(":common:core")
include(":common:network")
include(":common:navigation")
include(":feature:auth")
include(":feature:profile")
include(":feature:main")



