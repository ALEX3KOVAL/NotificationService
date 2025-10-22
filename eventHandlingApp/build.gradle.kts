plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadEventingGithubPackages()
    loadEventerGitHubPackage(EventerType.KAFKA)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":configuration"))
    implementation(project(":appImpl"))
    implementation(project(":common"))

    implementation("alex3koval:eventing-contract:latest.release")
    implementation("alex3koval:eventing-impl:latest.release")
    implementation("alex3koval:kafka-eventer:latest.release")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("io.projectreactor:reactor-core:3.4.40")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter")

    //implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:4.3.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //implementation("org.springframework.boot:spring-boot-starter-data-rest")
    //implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    val copyDeps = register("copyDeps", Copy::class) {
        from(configurations.runtimeClasspath.get())
        into(rootProject.layout.buildDirectory.dir("libs"))
        outputs.upToDateWhen { true }
    }

    val copyResources = register("copyResources", Copy::class) {
        from(sourceSets.main.get().resources)
        into(rootProject.layout.buildDirectory.dir("resources").get().asFile.absolutePath)

        outputs.upToDateWhen { true }
    }

    bootJar {
        destinationDirectory.set(rootProject.layout.buildDirectory.get())
        archiveFileName.set("eventerApp.jar")

        manifest {
            attributes.apply {
                put("Class-Path", configurations.runtimeClasspath.get()
                    .filter { it.extension == "jar" }
                    .distinctBy { it.name }
                    .joinToString(separator = " ", transform = { "libs/${it.name}" }))
            }
        }

        dependsOn(copyDeps)
        dependsOn(copyResources)
    }
}
