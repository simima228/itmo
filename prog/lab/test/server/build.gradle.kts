plugins {
    application
    id("com.gradleup.shadow") version "8.3.0"
}

application {
    mainClass.set("Main")
}

dependencies {
    implementation(project(":common"))
    implementation("org.apache.logging.log4j:log4j-core:2.25.4")
    implementation("org.apache.logging.log4j:log4j-api:2.25.3")
}

tasks.shadowJar {
    archiveClassifier.set("")

    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

tasks.jar {
    enabled = false
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Явная зависимость startScripts от shadowJar
tasks.startScripts {
    dependsOn(tasks.shadowJar)
}
