plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.alex3koval"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    loadEventingContractGithubPackage()
    loadTransactionalOutBoxGitHubPackage()
}

dependencies {
    implementation("alex3koval:eventing-contract:latest.release")
    implementation("alex3koval:transactional-outbox:latest.release")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
        archiveFileName.set("eventProducerApp.jar")

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
