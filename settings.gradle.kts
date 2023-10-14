pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "MinecraftBaseLibraries"
include("commandlib")
include("configlib")
include("pluginlib")
