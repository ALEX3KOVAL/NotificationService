plugins {
    id("java")
}

group = "ru.alex3koval.notificationService"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    loadEventingGithubPackages()
}
