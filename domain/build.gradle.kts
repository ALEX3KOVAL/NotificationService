plugins {
    id("java")
}

group = "ru.alex3koval.notificationService"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadEventingGithubPackages()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("alex3koval:eventing-contract:1.13.20")
    implementation("io.projectreactor:reactor-core:3.4.40")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}