allprojects {
    group = "com.hiddendesigner"
    version = "1.0.0"

    repositories {
        mavenCentral()
        gradlePluginPortal() // нужно для плагина shadow
    }
}

subprojects {
    apply(plugin = "java")

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}