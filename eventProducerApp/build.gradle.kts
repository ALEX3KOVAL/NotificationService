plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadTransactionalOutBoxGitHubPackage()
}

dependencies {
    implementation("alex3koval:transactional-outbox:latest.release")

    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}