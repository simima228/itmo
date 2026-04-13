plugins {
    java
}

dependencies {
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "Common Module",
            "Implementation-Version" to project.version
        )
    }
}