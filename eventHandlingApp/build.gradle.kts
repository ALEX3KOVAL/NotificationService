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
    implementation(project(":configuration"))
    implementation(project(":domain"))
    implementation(project(":eventingContract"))
    implementation(project(":eventingImpl"))

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.springframework.boot:spring-boot-starter-amqp:3.2.1")
    implementation("org.springframework.amqp:spring-rabbit-stream")

    implementation("org.springframework:spring-context:6.2.7")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    val copyDeps = register("copyDeps", Copy::class) {
        from(configurations.runtimeClasspath.get())
        into(rootProject.layout.buildDirectory.dir("libs"))
        outputs.upToDateWhen { true }
    }

    bootJar {
        destinationDirectory.set(rootProject.layout.buildDirectory.get())
        archiveFileName.set("eventHandlingApp.jar")
        archiveClassifier = "all"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)

        from(dependencies)

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
