plugins {
    id("java")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(16))

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}
