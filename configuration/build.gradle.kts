plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval.notificationService"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":eventingContract"))
    implementation(project(":eventingImpl"))
    implementation(project(":appImpl"))
    implementation(project(":storage"))

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework:spring-context:6.2.7")
    implementation("org.springframework:spring-webflux:6.2.9")
    implementation("org.springframework.boot:spring-boot-starter-amqp:3.2.1")
    implementation("org.springframework.amqp:spring-rabbit-stream")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
