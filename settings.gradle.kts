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
    }
}

rootProject.name = "Full Application"
include(
    ":app",
    ":login",
    ":home",
    ":libs:database",
    ":libs:network",
    ":libs:common",
    ":libs:navigation",
    ":libs:core",
    ":libs:components",
    ":menu"
)
