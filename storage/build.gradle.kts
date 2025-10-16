plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval.notificationService"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadEventingGithubPackages()
}

dependencies {
    implementation(project(":domain"))
    implementation("alex3koval:eventing-contract:latest.release")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.postgresql:r2dbc-postgresql:1.0.7.RELEASE")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.projectreactor:reactor-core")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
