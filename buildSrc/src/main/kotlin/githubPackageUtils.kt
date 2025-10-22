import loadGithubPackage
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.create
import java.net.URI

private fun RepositoryHandler.loadGithubPackage(repo: String) {
    maven {
        name = "GitHubPackages"
        url = URI("https://maven.pkg.github.com/ALEX3KOVAL/$repo")
        credentials {
            username = "ALEX3KOVAL"
            password = "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"
        }
        authentication {
            create<BasicAuthentication>("basic")
        }

        metadataSources {
            mavenPom()
            artifact()
        }

        content {
            includeGroup("alex3koval")
        }
    }
}

fun RepositoryHandler.loadEventingGithubPackages() {
    listOf("eventingContract", "eventingImpl").forEach(::loadGithubPackage)
}

fun RepositoryHandler.loadEventingContractGithubPackage() {
    loadGithubPackage("eventingContract")
}

fun RepositoryHandler.loadEventingImplGithubPackage() {
    loadGithubPackage("eventingImpl")
}

fun RepositoryHandler.loadTransactionalOutBoxGitHubPackage() {
    loadGithubPackage("transactionalOutBox")
}

fun RepositoryHandler.loadEventerGitHubPackage(eventerType: EventerType) {
    loadGithubPackage(eventerType.repo)
}
