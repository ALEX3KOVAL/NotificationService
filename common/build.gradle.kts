plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval.notificationService"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadEventingImplGithubPackage()
}

dependencies {
    implementation(project(":domain"))
    implementation("alex3koval:eventing-impl:latest.release")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter")
}

tasks.test {
    useJUnitPlatform()
}