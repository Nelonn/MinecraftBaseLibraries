pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "MinecraftBaseLibrary"
include("commandlib")
include("configlib")
include("pluginlib")
