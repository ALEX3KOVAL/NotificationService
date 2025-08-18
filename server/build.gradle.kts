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
    implementation(project(":configuration"))
    implementation(project(":appImpl"))

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-amqp:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.amqp:spring-rabbit-stream")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    val copyDeps = register("copyDeps", Copy::class) {
        from(configurations.runtimeClasspath.get())
        into(rootProject.layout.buildDirectory.dir("libs"))
        outputs.upToDateWhen { true }
    }

    bootJar {
        destinationDirectory.set(rootProject.layout.buildDirectory.get())
        archiveFileName.set("notificationService.jar")

        manifest {
            attributes.apply {
                put("Class-Path", configurations.runtimeClasspath.get()
                    .filter { it.extension == "jar" }
                    .distinctBy { it.name }
                    .joinToString(separator = " ", transform = { "libs/${it.name}" }))
            }
        }

        dependsOn(copyDeps)
    }
}

tasks.test {
    useJUnitPlatform()
}
